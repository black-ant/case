<!DOCTYPE html>
<!--[if lt IE 7 ]>
<html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]>
<html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]>
<html class="ie ie8" lang="en"> <![endif]-->
<!--[if IE 9 ]>
<html class="ie ie9" lang="en"> <![endif]-->
<!--[if (gte IE 10)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->

<!-- Mirrored from edena-creative-multipurpose-bootstrap-theme.little-neko.com/files/home-1.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 25 Aug 2015 04:20:22 GMT -->
<!-- Added by HTTrack -->
<meta http-equiv="content-type" content="text/html;charset=UTF-8"/><!-- /Added by HTTrack -->
<head>

    <!-- Basic Page Needs ================================================== -->
    <meta charset="utf-8">
    <title>EDENA HTML website template by Little Neko</title>
    <meta name="description" content="neko-description">
    <meta name="author" content="Little NEKO">
    <!-- Mobile Specific Metas ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- CSS ================================================== -->
    <!-- web font  -->
    <link href='http://fonts.googleapis.com/css?family=Ubuntu:400,700' rel='stylesheet' type='text/css'>
    <!-- Neko framework  -->
    <link type="text/css" rel="stylesheet" href="/css/custom-icons.css">
    <link type="text/css" rel="stylesheet" href="/css/external-plugins.min.css">
    <link type="text/css" rel="stylesheet" href="/css/neko-framework-layout.css">
    <link type="text/css" rel="stylesheet" id="color" href="/css/neko-framework-color.css">

    <script src="/js/modernizr.custom.js"></script>

</head>
<body class="activate-appear-animation header-7 parallaxed-footer">

<!-- global-wrapper -->
<div id="global-wrapper">
    <!-- content -->
    <main id="content">
        <!-- Why choose EDENA? -->
        <section class="pb-medium pt-medium" id="choose">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center heading" data-nekoanim="fadeInUp" data-nekodelay="100">
                        <h1>FreeMarker 常见用法</h1>
                        <h2>欢迎 : ${user.username}</h2>
                    </div>
                </div>
                <div class="row">

                    <div class="col-md-12  text-center">
                        <!-- tabs -->
                        <!-- Navigation Buttons -->

                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#responsive" data-toggle="tab"><i class="icon-star"></i>输出</a>
                            </li>
                            <li>
                                <a href="#profile" data-toggle="tab"><i class="icon-star"></i>判断</a>
                            </li>
                            <li>
                                <a href="#messages" data-toggle="tab"><i class="icon-star"></i>设置</a>
                            </li>
                            <li>
                                <a href="#headers" data-toggle="tab"><i class="icon-star"></i>替换</a>
                            </li>
                        </ul>

                        <!-- Navigation Buttons -->

                        <!-- Content -->

                        <div class="tab-content">

                            <div class="tab-pane fade in active" id="responsive">
                                <div class="row">
                                    <div class="col-md-8 text-left">

                                        <h2>List 输出</h2>
                                        <p>
                                            <#assign seq = ["winter", "spring", "summer", "autumn"]>
                                            <#list seq as x>
                                                  ${x_index + 1}. ${x}<#if x_has_next>,</#if>
                                            </#list>
                                        </p>
                                        <h3>noparse 输出</h3>
                                        <#noparse>
                                        <#list books as book>
                                          
                                        <tr>
                                            <td>${book.name}
                                            <td>作者:${book.author}
                                        </tr>
                                        </#list>
                                        </#noparse>

                                </div>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="profile">
                            <div class="row">
                                <div class="col-md-8 text-left">
                                    <h2>if else 判断</h2>
                                    <p>
                                        <#assign age=11>
                                        <#if (age>3&&age<10)>中级
                                        <#elseif (age>10&&age<15)>高级
                                        <#elseif (age>15)>在线炒粉
                                        <#else> 初级
                                        </#if>
                                    </p>
                                    <h2>swith 判断</h2>
                                    <p>
                                        <#assign age2=11>
                                        <#switch age2>
                                            <#case 11>阿里门口炒粉<#break>
                                            <#case 12>腾讯门口炒粉<#break>
                                            <#default>开公司炒粉
                                        </#switch>
                                    </p>
                                </div>
                            </div>
                        </div>


                        <div class="tab-pane fade" id="messages">
                            <div class="row">
                                <div class="col-md-8 text-left">

                                    <h2>Setting</h2>
                                    <p>
                                        ${1.2}
                                        <#setting locale="en_US">
                                        ${1.2}
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="headers">
                            <div class="row">
                                <div class="col-md-9 text-left">

                                    <h2>macro</h2>
                                    <p>
                                        <#macro book>
                                            j2ee
                                        </#macro>
                                        <@book/> 
                                    </p>
                                    <p>
                                        <#macro book booklist>    
                                            <#list booklist as book>
                                                ${book}
                                            </#list>
                                        </#macro>
                                        <@book booklist=["spring","j2ee"] />  
                                    </p>

                                    <h2>escape</h2>
                                    <p>
                                        <#escape x as x?html>
                                            First name:${user.username}
                                        </#escape>
                                    </p>

                                </div>
                            </div>
                        </div>


                    </div>

                    <!-- tabs -->

                </div>
            </div>
</div>
</section>
<!-- / Why choose EDENA? -->

<!-- call to action -->
<section class="pb-medium pt">
    <div class="container">
        <div class="row">
            <div class="col-md-12">

                <div class="cta-box">
                    <div class="cta-box-text">
                        <h1>
                            欢迎访问我的博客了解更多 !
                        </h1>

                    </div>
                    <div class="cta-box-btn">
                        <a class="btn primary large" title="EDENA website template on themeforest"
                           href="http://themeforest
                                   .net/item/edena-creative-multipurpose-bootstrap-template/9662339?ref=Little-Neko"
                           target="_blank" onclick="redirect()">
                            <i class="icon-down-open-big"></i>show now
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- / call to action -->

</main>
<!-- / content -->

</div>
<!-- global wrapper -->

<!-- End Document ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src=/js/jquery-ui-1.8.23.custom.min.js"></script>

<!-- external framework plugins -->
<script type="application/javascript" src="/js/external-plugins.min.js"></script>
<!-- neko framework script -->
<script type="text/javascript" src="/js/neko-framework.js"></script>


<!-- neko custom script -->
<script src="js/custom.js"></script>

<script>
    function redirect() {
        window.location.href = "http://www.antblack.xyz";
    }
</script>

</body>

<!-- Mirrored from edena-creative-multipurpose-bootstrap-theme.little-neko.com/files/home-1.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 25 Aug 2015 04:20:45 GMT -->
</html>
