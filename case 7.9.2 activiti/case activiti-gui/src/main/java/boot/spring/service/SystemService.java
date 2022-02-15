package boot.spring.service;

import java.util.List;

import boot.spring.po.Permission;
import boot.spring.po.Role;
import boot.spring.po.User;
import boot.spring.po.UserRole;


public interface SystemService {
	List<User> getallusers();
	List<User> getpageusers(int pagenum, int pagesize);
	User getUserByid(int id);
	List<Role> getRoles();
	void deleteuser(int uid);
	void adduser(User user,String[] rolenames);
	void adduser(User user);//只添加用户，无角色添加
	void updateuser(int uid,User user,String[] rolenames);
	List<Role> getRoleinfo();
	List<Permission> getPermisions();
	/**
	 * 使用用户id获取角色id列表
	 * @param userid
	 * @return
	 */
	List<UserRole> listRolesByUserid(int userid);
	void addrole(Role role, String[] permissionnames);
	void deleterole(int rid);
	Role getRolebyid(int rid);
	void deleterolepermission(int rid);//删除rid的角色下的所有权利
	void updaterole(int rid,String[] permissionnames);//把所有的权利permissionnames添加到rid的角色下
	List<Permission> getPagePermisions(int pagenum, int pagesize);
	void addPermission(String permissionname);
	void deletepermission(int pid);
	int getUidByusername(String username);
}
