package com.gang.study.ldap.demo.utils;

import com.gang.study.ldap.demo.service.LDAPConnect;
import com.gang.study.ldap.demo.to.DefaultProperties;
import com.gang.study.ldap.demo.to.LDAPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Classname ADUtils
 * @Description TODO
 * @Date 2020/8/7 22:11
 * @Created by zengzg
 */
public final class ADUtils {

    private static Logger LOG = LoggerFactory.getLogger(ADUtils.class);

    public static final int UF_ACCOUNTDISABLE = 0x0002;

    public static final String UACCONTROL_ATTR = "userAccountControl";

    private ADUtils() {
        super();
    }

    /**
     * 获取  Attributes 中 OU Name(LDAP_OU_NAME) {@link DefaultProperties }
     *
     * @param attributes
     * @return Attribute
     */
    public static Attribute getOuNameAttr(Attributes attributes) {
        // 获取OUName
        Attribute ouNameAttr = attributes.get(DefaultProperties.LDAP_OU_NAME);
        ouNameAttr = ouNameAttr == null ? attributes.get(DefaultProperties.LDAP_SHOW_NAME) : ouNameAttr;
        return ouNameAttr;
    }

    /**
     * 判断是否为DN
     *
     * @param entryDN
     * @return
     */
    public static Boolean isDN(String entryDN) {
        if (entryDN.contains("DC=")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断是否为objectGUID
     *
     * @param key
     * @return
     */
    public static Boolean isGUID(String key) {
        if (key.length() > 30) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param entryDN
     * @return
     */
    public static String entryDNName(String entryDN) {
        String indexSign = entryDN.substring(2).indexOf(DefaultProperties.LDAP_OU_UP) > 0
                ? DefaultProperties.LDAP_OU_UP
                : DefaultProperties.LDAP_DC_UP;

        return entryDN.substring(3, entryDN.substring(2).indexOf(indexSign + "=") + 1);  //4月19日调整 避免名称中包含OU
    }

    /**
     * 获取entryDN 父组织路径
     *
     * @param entryDN
     * @return
     */
    public static String entryDNParent(String entryDN) {

        // example 1 : OU=武汉研发,DC=eicdevdomain,DC=in
        // example 2 : OU=Org254,OU=武汉研发,DC=eicdevdomain,DC=in

        String indexSign = entryDN.substring(2).indexOf(DefaultProperties.LDAP_OU_UP) > 0
                ? DefaultProperties.LDAP_OU_UP
                : DefaultProperties.LDAP_DC_UP;

        return entryDN.substring(entryDN.substring(2).indexOf(indexSign + "=") + 2); //4月19日调整 避免名称中包含OU
    }


    /**
     * 获取 ParentDN
     *
     * @param dn
     * @return
     */
    public static String getParentDN(String dn) {
        return dn.replaceFirst("([^=]+)=([^,]+),", "");
    }

    /**
     * 获取指定的路径名称
     *
     * @param entryDN
     * @param index   : 获取路径中的第几位
     * @return
     */
    public static String getPathNameByIndex(String entryDN, Integer index) {
        String[] backStr = entryDN.split("(DC=|CN=|OU=)");
        return index == backStr.length ? backStr[index + 1] : backStr[index + 1];
    }

    /**
     * @Title: addGroupe
     * @Description: 通过修改member 修改组
     * @param: @param groupDN
     * @param: @param entryDN
     * @return: void
     */
    public static void addGroup(LdapContext ctx, String groupDN, String entryDN) {
        operationAttribute(ctx, DirContext.ADD_ATTRIBUTE, groupDN, "member", entryDN);
    }

    /**
     * 批量加组操作
     *
     * @param ctx
     * @param groupDN
     * @param entryDN
     */
    public static void addGroup(LdapContext ctx, String groupDN, List<String> entryDN) {
        try {
            ModificationItem[] mods = new ModificationItem[entryDN.size()];
            // 修改属性
            for (int i = 0; i < entryDN.size(); i++) {
                BasicAttribute attr0 = new BasicAttribute("member", entryDN.get(i));
                mods[i] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            }
            ctx.modifyAttributes(groupDN, mods);
        } catch (Exception e) {
            LOG.error("批量加组失败 : dn -> {0} , type -> {1} ,value -> {2}", groupDN, DirContext.ADD_ATTRIBUTE, entryDN);
            e.printStackTrace();

        }
    }

    /**
     * 移除组
     *
     * @param ctx
     * @param groupDN
     * @param entryDN
     */
    public static void removeGroup(LdapContext ctx, String groupDN, String entryDN) {
        operationAttribute(ctx, DirContext.REMOVE_ATTRIBUTE, groupDN, "member", entryDN);
    }

    /**
     * -> 修改属性 DirContext.ADD_ATTRIBUTE : 添加属性--1<br>
     * DirContext.REMOVE_ATTRIBUTE : 删除属性--3<br>
     * DirContext.REPLACE_ATTRIBUTE :替换属性--2<br>
     *
     * @param type       : 操作类型
     * @param sourceDN   : 被操作对象
     * @param filedname  : 操作属性
     * @param fieldValue : 修改的值
     */
    public static void operationAttribute(LdapContext ctx, int type, String sourceDN, String filedname,
                                          String fieldValue) {
        LOG.info("---> 修改属性 :sourceDN :{} -- name :{} -- value :{}", sourceDN, filedname, fieldValue);
        try {
            ModificationItem[] mods = new ModificationItem[1];
            // 修改属性
            BasicAttribute attr0 = new BasicAttribute(filedname, fieldValue);
            mods[0] = new ModificationItem(type, attr0);
            ctx.modifyAttributes(sourceDN, mods);
        } catch (NameAlreadyBoundException e) {
            LOG.info("------> this User is already in this Group <-------");
        } catch (Exception e) {
        }
    }

    /**
     * 特殊字符处理(支持 ? : )
     * 默认转义(+ > ) : 此类图标转义后 , CN 中正常 ,但是 OU 中会默认加入 \ , LDAP 规则强制要求
     * 部分字符会默认转义
     * <p>
     * 注意 : 强烈建议不要用等号 , OU 中大量使用了等号 , 等号过多后续会极难控制
     *
     * @param entryDN
     * @return
     */
    public static String exchangeIllegalEntryDN(String entryDN) {

        entryDN = entryDN.replaceAll("/", "\\\\2F");
        entryDN = entryDN.replaceAll("\\?", "\\\\3F");
        entryDN = entryDN.replaceAll("\\+", "\\\\2B");

        // TODO : = 号无法处理
        //        entryDN = entryDN.replaceAll("\\=", "\\\\3D");
        entryDN = entryDN.replaceAll("\\:", "\\\\3A");
        entryDN = entryDN.replaceAll("\\>", "\\\\3E");

        // DOTO : 后续新特殊字符可以继续添加...

        return entryDN;
    }


    /**
     * 逆向转换
     *
     * @param entryDN
     * @return
     */
    public static String checkEntryDNTurn(String entryDN) {
        entryDN = entryDN.replaceAll("\\\\2F", "/");
        return entryDN;
    }


    public static String getValueByField(Attributes attrs, String field) {
        String value = "";
        try {
            Attribute attribute = attrs.get(field);
            if (attribute != null) {
                value = String.valueOf(attribute.get(0));
            }
        } catch (NamingException e) {
            LOG.error("get" + field + "fail");
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 获取用户状态
     *
     * @param attrs
     * @param status
     * @return
     */
    public static Attributes processUserStatus(Attributes attrs, Boolean status) {
        long uacValue = Long.parseLong(getValueByField(attrs, UACCONTROL_ATTR));

        Boolean checkUserEnableStatus = checkUserDisableStatus(uacValue);
        Attributes attributes = new BasicAttributes();
        //        attributes.put(attrs.get(DefaultProperties.LDAP_CN));

        // 状态一致
        if (status == !checkUserEnableStatus) {
            return null;
        } else {
            if (status) {
                // if not enabled yet --> enable removing 0x00002
                if (uacValue % 16 == UF_ACCOUNTDISABLE) {
                    uacValue -= UF_ACCOUNTDISABLE;
                }
            } else {
                // if not disabled yet --> disable adding 0x00002
                if (uacValue % 16 != UF_ACCOUNTDISABLE) {
                    uacValue += UF_ACCOUNTDISABLE;
                }
            }
            // attributes.put(new BasicAttribute(PASSWORD_ATTR, password));
            attributes.put(new BasicAttribute(UACCONTROL_ATTR, String.valueOf(uacValue)));
        }
        return attributes;
    }

    // 检测用户是否禁用 禁用返回true,启用返回false
    public static Boolean checkUserDisableStatus(long userAccountControl) {
        if (userAccountControl >= 16777216) // TRUSTED_TO_AUTH_FOR_DELEGATION - 允许该帐户进行委派
        {
            userAccountControl = userAccountControl - 16777216;
        }
        if (userAccountControl >= 8388608) // PASSWORD_EXPIRED - (Windows 2000/Windows Server 2003) 用户的密码已过期
        {
            userAccountControl = userAccountControl - 8388608;
        }
        if (userAccountControl >= 4194304) // DONT_REQ_PREAUTH
        {
            userAccountControl = userAccountControl - 4194304;
        }
        if (userAccountControl >= 2097152) // USE_DES_KEY_ONLY - (Windows 2000/Windows Server 2003) 将此用户限制为仅使用数据加密标准
        // (DES) 加密类型的密钥
        {
            userAccountControl = userAccountControl - 2097152;
        }
        if (userAccountControl >= 1048576) // NOT_DELEGATED - 设置此标志后，即使将服务帐户设置为信任其进行 Kerberos 委派，也不会将用户的安全上下文委派给该服务
        {
            userAccountControl = userAccountControl - 1048576;
        }
        if (userAccountControl >= 524288) // TRUSTED_FOR_DELEGATION - 设置此标志后，将信任运行服务的服务帐户（用户或计算机帐户）进行 Kerberos
        // 委派。任何此类服务都可模拟请求该服务的客户端。若要允许服务进行 Kerberos 委派，必须在服务帐户的 userAccountControl
        // 属性上设置此标志
        {
            userAccountControl = userAccountControl - 524288;
        }
        if (userAccountControl >= 262144) // SMARTCARD_REQUIRED - 设置此标志后，将强制用户使用智能卡登录
        {
            userAccountControl = userAccountControl - 262144;
        }
        if (userAccountControl >= 131072) // MNS_LOGON_ACCOUNT - 这是 MNS 登录帐户
        {
            userAccountControl = userAccountControl - 131072;
        }
        if (userAccountControl >= 65536) // DONT_EXPIRE_PASSWORD-密码永不过期
        {
            userAccountControl = userAccountControl - 65536;
        }
        if (userAccountControl >= 2097152) // MNS_LOGON_ACCOUNT - 这是 MNS 登录帐户
        {
            userAccountControl = userAccountControl - 2097152;
        }
        if (userAccountControl >= 8192) // SERVER_TRUST_ACCOUNT - 这是属于该域的域控制器的计算机帐户
        {
            userAccountControl = userAccountControl - 8192;
        }
        if (userAccountControl >= 4096) // WORKSTATION_TRUST_ACCOUNT - 这是运行 Microsoft Windows NT 4.0
        // Workstation、Microsoft Windows NT 4.0 Server、Microsoft Windows 2000
        // Professional 或 Windows 2000 Server 并且属于该域的计算机的计算机帐户
        {
            userAccountControl = userAccountControl - 4096;
        }
        if (userAccountControl >= 2048) // INTERDOMAIN_TRUST_ACCOUNT - 对于信任其他域的系统域，此属性允许信任该系统域的帐户
        {
            userAccountControl = userAccountControl - 2048;
        }
        if (userAccountControl >= 512) // NORMAL_ACCOUNT - 这是表示典型用户的默认帐户类型
        {
            userAccountControl = userAccountControl - 512;
        }

        if (userAccountControl >= 256) // TEMP_DUPLICATE_ACCOUNT -
        // 此帐户属于其主帐户位于另一个域中的用户。此帐户为用户提供访问该域的权限，但不提供访问信任该域的任何域的权限。有时将这种帐户称为“本地用户帐户”
        {
            userAccountControl = userAccountControl - 256;
        }
        if (userAccountControl >= 128) // ENCRYPTED_TEXT_PASSWORD_ALLOWED - 用户可以发送加密的密码
        {
            userAccountControl = userAccountControl - 128;
        }
        if (userAccountControl >= 64) // PASSWD_CANT_CHANGE - 用户不能更改密码。可以读取此标志，但不能直接设置它
        {
            userAccountControl = userAccountControl - 64;
        }
        if (userAccountControl >= 32) // PASSWD_NOTREQD - 不需要密码
        {
            userAccountControl = userAccountControl - 32;
        }
        if (userAccountControl >= 16) // LOCKOUT
        {
            userAccountControl = userAccountControl - 16;
        }
        if (userAccountControl >= 8) // HOMEDIR_REQUIRED - 需要主文件夹
        {
            userAccountControl = userAccountControl - 8;
        }

        if (userAccountControl >= 2) {
            return true;
        }
        return false;

    }

    public static byte[] encodePassword(String password) {
        String newQuotedPassword = "\"" + password + "\"";
        byte[] newUnicodePassword = null;
        try {
            newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
        } catch (UnsupportedEncodingException e) {
            LOG.error("encodePassword " + password + " fail");
            e.printStackTrace();
        }
        return newUnicodePassword;
    }

    public static String decodePassword(byte[] encodePassword) {
        String password = "";
        try {
            password = new String(encodePassword, "UTF-16LE");
        } catch (UnsupportedEncodingException e) {
            LOG.error("decodePassword " + encodePassword + " fail");
            e.printStackTrace();
        }
        return password;
    }

}

