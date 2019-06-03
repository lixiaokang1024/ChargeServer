<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="IE=7" http-equiv="X-UA-Compatible" />
    <link rel="stylesheet" type="text/css" href="css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="css/frame.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/menu.js"></script>
    <title>北京电子商务 - Powered By SfShop</title>
    <link rel="shortcut icon" href="http://corp.sfbest.com/favicon.ico" />
    <link type="text/css" href="css/easyui/themes/default/easyui.css" rel="stylesheet" />
    <link type="text/css" href="css/admpanel.css" rel="stylesheet" media="screen, projection" />
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <script type="text/javascript" src="js/common.js"></script>

    <script>
        var ROOTDIR = "";
    </script>

    <script type="text/javascript">
        function addTab(title, url,flag){
            if ($('#tab').tabs('exists', title)){
                //$('#tab').tabs('select', title);
                $('#tab').tabs('close', title);
            }
            var content = "<iframe scrolling='auto' frameborder='0'  src='"+url+"' style='width:100%;height:100%;'></iframe>";
            $('#tab').tabs('add',{
                title:title,
                content:content,
                closable:flag,
                cache:false
            });
        }
        $(function(){
            $('#editPass').live('click',function(){
                $.dialog({title:'修改密码',width:500,height:300,url:$(this).attr('href')});
                return false;
            });
        })

        function gourl(){
            $('#topMenu a:first').click();
            var flag = false;
            $('#left a').each(function(){
                var regone = /库存管理/;
                var regtwo = /库存预警/;
                if(flag==false) {
                    if(regone.test($(this).text())){
                        flag = true;
                        $(this).click();
                    }
                }
            });
            addTab("库存预警",ROOTDIR+"/stockWarning/index",true);
        }
    </script>

</head>
<body class="easyui-layout" onLoad="addTab('后台首页', 'def.html',false)">
<div region="north"  style="height:auto;padding:0px; overflow:hidden;">
    <div id="header" class="header" >
        <div class="wms_logo"></div>
        <div class="hd2" id="topMenu" >
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_10">
                    <span>商品管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_12">
                    <span>用户管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_15">
                    <span>销售管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_14">
                    <span>页面管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_17">
                    <span>统计管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_21">
                    <span>工具箱</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_32">
                    <span>网站配置</span>
                </a>
            </div>
        </div>
        <div class="top-link">
            <img src="images/wms/contact_blue.gif"> 304184<img src="images/wms/lock_edit.gif"><a href="/default/editPass" id="editPass">修改密码</a>
            <img src="images/wms/page_go.gif"><a href="/site/logout">退出</a>
        </div>
        <div class="wms_yj" >
            <p style='cursor:pointer;' title='库存预警' onclick='gourl()'  >您的分类下有<font color='red'>497</font>个商品出现库存报警</p>
        </div>
    </div>
</div>
<div id="left" region="west" split="true" title="" style="padding:0px;overflow:hidden;margin:0;overflow:auto; background:#417eb7;">
    <ul id="menu">
        <li class="leftMenu menu_10">
            <a class="parent" href="javascript:void(0);">商品管理</a>
            <ul>
                <li>
                    <span><a href="list.html" title="商品列表" class="newTab">商品列表						</a></span>
                    <span><a href="list.html" title="待审商品" class="newTab">待审商品						</a></span>
                    <span>
						<a href="/inprice/index"
                           title="基准进价调整" class="newTab">
							基准进价调整						</a>
						</span>
                    <span>
						<a href="/detailBlockTemplate/index"
                           title="详情模板" class="newTab">
							详情模板						</a>
						</span>
                    <span>
						<a href="/productPicture/batchUpload"
                           title="批量传图" class="newTab">
							批量传图						</a>
						</span>
                    <span>
						<a href="/productNewTag/index"
                           title="商品标签管理" class="newTab">
							商品标签管理						</a>
						</span>
                    <span>
						<a href="/preSell/index"
                           title="预售管理" class="newTab">
							预售管理						</a>
						</span>
                    <span>
						<a href="/productPriceChange/index"
                           title="售价调整" class="newTab">
							售价调整						</a>
						</span>
                    <span>
						<a href="/category/index"
                           title="商品分类" class="newTab">
							商品分类						</a>
						</span>
                    <span>
						<a href="/productCorrection/index"
                           title="商品信息纠错" class="newTab">
							商品信息纠错						</a>
						</span>
                    <span>
						<a href="/attribute/index"
                           title="扩展属性" class="newTab">
							扩展属性						</a>
						</span>
                    <span>
						<a href="/productChangePrice/index"
                           title="商品进价调整记录" class="newTab">
							商品进价调整记录						</a>
						</span>
                    <span>
						<a href="/product/recycle"
                           title="还原删除商品" class="newTab">
							还原删除商品						</a>
						</span>
                    <span>
						<a href="/sfvProduct/index"
                           title="时令优选商品列表" class="newTab">
							时令优选商品列表						</a>
						</span>
                    <span>
						<a href="/sfvCategory/index"
                           title="时令优选商品分类" class="newTab">
							时令优选商品分类						</a>
						</span>
                    <span>
						<a href="/sfvProductNode/index"
                           title="时令优选商品节点图片管理" class="newTab">
							时令优选商品节点图片管理						</a>
						</span>
                    <span>
						<a href="/sfvArea/index"
                           title="时令商品配送区域组管理" class="newTab">
							时令商品配送区域组管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_10">
            <a class="parent" href="javascript:void(0);">品牌管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/brand/index"
                           title="品牌列表" class="newTab">
							品牌列表						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_10">
            <a class="parent" href="javascript:void(0);">评论咨询</a>
            <ul>
                <li>
			            						<span>
						<a href="/comments/auditCommentList"
                           title="已审核评论" class="newTab">
							已审核评论						</a>
						</span>
                    <span>
						<a href="/comments/waitAuditCommentList"
                           title="待审核评论" class="newTab">
							待审核评论						</a>
						</span>
                    <span>
						<a href="/comments/indexSun"
                           title="晒单信息" class="newTab">
							晒单信息						</a>
						</span>
                    <span>
						<a href="/score/index"
                           title="评分设置" class="newTab">
							评分设置						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_10">
            <a class="parent" href="javascript:void(0);">库存管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/stockWarning/index"
                           title="库存预警" class="newTab">
							库存预警						</a>
						</span>
                    <span>
						<a href="/stockWarning/argumentList"
                           title="批量设置预警参数" class="newTab">
							批量设置预警参数						</a>
						</span>
                    <span>
						<a href="/stock/index"
                           title="分仓库存查看" class="newTab">
							分仓库存查看						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_12">
            <a class="parent" href="javascript:void(0);">用户管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/gusers/index"
                           title="用户列表" class="newTab">
							用户列表						</a>
						</span>
                    <span>
						<a href="/gusersBlack/index"
                           title="黑名单列表" class="newTab">
							黑名单列表						</a>
						</span>
                    <span>
						<a href="/msg/index"
                           title="站内消息" class="newTab">
							站内消息						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">订单管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/order/index"
                           title="所有订单列表" class="newTab">
							所有订单列表						</a>
						</span>
                    <span>
						<a href="/order/initial"
                           title="回滚期订单列表" class="newTab">
							回滚期订单列表						</a>
						</span>
                    <span>
						<a href="/order/waitaudit"
                           title="待审核订单" class="newTab">
							待审核订单						</a>
						</span>
                    <span>
						<a href="/order/waitpay"
                           title="待支付订单" class="newTab">
							待支付订单						</a>
						</span>
                    <span>
						<a href="/order/outofstock"
                           title="缺货订单列表" class="newTab">
							缺货订单列表						</a>
						</span>
                    <span>
						<a href="/order/waitFiscalCheck"
                           title="待财务确认收款" class="newTab">
							待财务确认收款						</a>
						</span>
                    <span>
						<a href="/order/stocking"
                           title="待备货订单列表" class="newTab">
							待备货订单列表						</a>
						</span>
                    <span>
						<a href="/order/shipped"
                           title="待发货订单列表" class="newTab">
							待发货订单列表						</a>
						</span>
                    <span>
						<a href="/order/receiving"
                           title="待确认收货订单" class="newTab">
							待确认收货订单						</a>
						</span>
                    <span>
						<a href="/order/complete"
                           title="已完成订单列表" class="newTab">
							已完成订单列表						</a>
						</span>
                    <span>
						<a href="/order/rejected"
                           title="退货订单列表" class="newTab">
							退货订单列表						</a>
						</span>
                    <span>
						<a href="/order/allrejected"
                           title="拒收订单列表" class="newTab">
							拒收订单列表						</a>
						</span>
                    <span>
						<a href="/order/cancel"
                           title="取消订单列表" class="newTab">
							取消订单列表						</a>
						</span>
                    <span>
						<a href="/order/invalid"
                           title="无效订单列表" class="newTab">
							无效订单列表						</a>
						</span>
                    <span>
						<a href="/order/finish"
                           title="已关闭订单" class="newTab">
							已关闭订单						</a>
						</span>
                    <span>
						<a href="/order/cps/index"
                           title="yiqifa订单列表" class="newTab">
							yiqifa订单列表						</a>
						</span>
                    <span>
						<a href="/order/sfv"
                           title="尊礼会订单列表" class="newTab">
							尊礼会订单列表						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">贺卡管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/greetingCards/index"
                           title="贺卡列表" class="newTab">
							贺卡列表						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">大客户订单管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/largeCustomerOrder/index"
                           title="大客户订单列表" class="newTab">
							大客户订单列表						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">订单商品管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/orderProduct/index"
                           title="订单商品管理" class="newTab">
							订单商品管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">促销管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/favourable/index"
                           title="优惠活动" class="newTab">
							优惠活动						</a>
						</span>
                    <span>
						<a href="/package/index"
                           title="组合套装" class="newTab">
							组合套装						</a>
						</span>
                    <span>
						<a href="/reduceprice/index"
                           title="单品直降管理" class="newTab">
							单品直降管理						</a>
						</span>
                    <span>
						<a href="/hampers/index"
                           title="推荐礼篮套装" class="newTab">
							推荐礼篮套装						</a>
						</span>
                    <span>
						<a href="/selfActivity/index"
                           title="自采商品活动" class="newTab">
							自采商品活动						</a>
						</span>
                    <span>
						<a href="/ActivityCode/index"
                           title="活动对码管理" class="newTab">
							活动对码管理						</a>
						</span>
                    <span>
						<a href="/favourable/index/act_type/12"
                           title="限时抢购管理" class="newTab">
							限时抢购管理						</a>
						</span>
                    <span>
						<a href="/reduceprice/auditMaori"
                           title="单品直降负毛利" class="newTab">
							单品直降负毛利						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">缺货商品管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/backorderProduct/index"
                           title="缺货商品列表" class="newTab">
							缺货商品列表						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">购物券管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/coupon/index"
                           title="优惠券列表" class="newTab">
							优惠券列表						</a>
						</span>
                    <span>
						<a href="/giftCoupon/index"
                           title="赠品券管理" class="newTab">
							赠品券管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">报表管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/report/purchase/customerReturn"
                           title="顾客退货" class="newTab">
							顾客退货						</a>
						</span>
                    <span>
						<a href="/report/purchase/negativeGrossMargin"
                           title="负毛利率" class="newTab">
							负毛利率						</a>
						</span>
                    <span>
						<a href="/report/purchase/nosale"
                           title="最近无销售商品" class="newTab">
							最近无销售商品						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">砸金蛋管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/goldenEgg/winningUsers"
                           title="中奖用户列表" class="newTab">
							中奖用户列表						</a>
						</span>
                    <span>
						<a href="/goldenEgg/addPrize"
                           title="手动添加中奖人" class="newTab">
							手动添加中奖人						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">礼品卡管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/giftCardBatch/index"
                           title="批次管理" class="newTab">
							批次管理						</a>
						</span>
                    <span>
						<a href="/giftCard/emptyList"
                           title="空值卡" class="newTab">
							空值卡						</a>
						</span>
                    <span>
						<a href="/giftCard/rechargeList"
                           title="已充值" class="newTab">
							已充值						</a>
						</span>
                    <span>
						<a href="/giftCard/preSaleList"
                           title="预售" class="newTab">
							预售						</a>
						</span>
                    <span>
						<a href="/giftCard/sellList"
                           title="销售中" class="newTab">
							销售中						</a>
						</span>
                    <span>
						<a href="/giftCard/goodsSoldList"
                           title="已销售" class="newTab">
							已销售						</a>
						</span>
                    <span>
						<a href="/giftCard/soonToExpire"
                           title="即将到期卡" class="newTab">
							即将到期卡						</a>
						</span>
                    <span>
						<a href="/giftCard/expired"
                           title="已过期卡" class="newTab">
							已过期卡						</a>
						</span>
                    <span>
						<a href="/giftCard/finished"
                           title="已用完卡" class="newTab">
							已用完卡						</a>
						</span>
                    <span>
						<a href="/giftType"
                           title="线上礼品卡类型" class="newTab">
							线上礼品卡类型						</a>
						</span>
                    <span>
						<a href="/giftBuy/index"
                           title="线上礼品卡订单" class="newTab">
							线上礼品卡订单						</a>
						</span>
                    <span>
						<a href="/giftCard/index"
                           title="礼品卡" class="newTab">
							礼品卡						</a>
						</span>
                    <span>
						<a href="/giftCard/count"
                           title="礼品卡统计" class="newTab">
							礼品卡统计						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">退换货管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/returns/list"
                           title="退换货收件" class="newTab">
							退换货收件						</a>
						</span>
                    <span>
						<a href="/returns/index"
                           title="退货单管理" class="newTab">
							退货单管理						</a>
						</span>
                    <span>
						<a href="/returns/exchange"
                           title="换货单管理" class="newTab">
							换货单管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_15">
            <a class="parent" href="javascript:void(0);">企业采购管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/company/index"
                           title="企业采购信息" class="newTab">
							企业采购信息						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">文章管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/article/index"
                           title="文章列表" class="newTab">
							文章列表						</a>
						</span>
                    <span>
						<a href="/artComm/index"
                           title="文章评论管理" class="newTab">
							文章评论管理						</a>
						</span>
                    <span>
						<a href="/article/mkart"
                           title="文章生成HTML" class="newTab">
							文章生成HTML						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">模板管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/source/index"
                           title="模板列表" class="newTab">
							模板列表						</a>
						</span>
                    <span>
						<a href="/templates/index"
                           title="系统邮件模板" class="newTab">
							系统邮件模板						</a>
						</span>
                    <span>
						<a href="/config/setStaticV"
                           title="静态文件版本" class="newTab">
							静态文件版本						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">栏目管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/column/index"
                           title="栏目列表" class="newTab">
							栏目列表						</a>
						</span>
                    <span>
						<a href="/column/mkcol"
                           title="栏目生成HTML" class="newTab">
							栏目生成HTML						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">专题管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/ztManage/index"
                           title="专题列表" class="newTab">
							专题列表						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">关键词管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/keyCategoryMap/index"
                           title="关键词列表" class="newTab">
							关键词列表						</a>
						</span>
                    <span>
						<a href="/config/index/type/0"
                           title="商品热门关键词" class="newTab">
							商品热门关键词						</a>
						</span>
                    <span>
						<a href="/config/index/type/1"
                           title="文章热门关键词" class="newTab">
							文章热门关键词						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">资源管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/filemanage/index"
                           title="资源列表" class="newTab">
							资源列表						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">HTML生成管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/html/allCategory"
                           title="全部品类页面生成" class="newTab">
							全部品类页面生成						</a>
						</span>
                    <span>
						<a href="/html/allBrand"
                           title="全部品牌页面生成" class="newTab">
							全部品牌页面生成						</a>
						</span>
                    <span>
						<a href="/html/makeCategoryOne"
                           title="顶级品类页面生成" class="newTab">
							顶级品类页面生成						</a>
						</span>
                    <span>
						<a href="/html/Product"
                           title="单品页HTML生成" class="newTab">
							单品页HTML生成						</a>
						</span>
                    <span>
						<a href="/html/ranking"
                           title="排行榜TXT生成" class="newTab">
							排行榜TXT生成						</a>
						</span>
                    <span>
						<a href="/html/makeCategorySide"
                           title="左侧公用品类列表" class="newTab">
							左侧公用品类列表						</a>
						</span>
                    <span>
						<a href="/html/makeListSpecial"
                           title="列表页特卖" class="newTab">
							列表页特卖						</a>
						</span>
                    <span>
						<a href="/html/makeListAdimg"
                           title="广告生成" class="newTab">
							广告生成						</a>
						</span>
                    <span>
						<a href="/html/makePublic"
                           title="公共头底部生成" class="newTab">
							公共头底部生成						</a>
						</span>
                    <span>
						<a href="/activity/puball"
                           title="活动页面全部更新" class="newTab">
							活动页面全部更新						</a>
						</span>
                    <span>
						<a href="/html/makeCard"
                           title="礼品卡购买页面" class="newTab">
							礼品卡购买页面						</a>
						</span>
                    <span>
						<a href="/html/makeSfv"
                           title="时令优选购买页面生成" class="newTab">
							时令优选购买页面生成						</a>
						</span>
                    <span>
						<a href="/html/packages"
                           title="组合套装生成" class="newTab">
							组合套装生成						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">页面模块元素管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/pagemodules/index"
                           title="页面模块列表" class="newTab">
							页面模块列表						</a>
						</span>
                    <span>
						<a href="/pageelements/index"
                           title="页面元素列表" class="newTab">
							页面元素列表						</a>
						</span>
                    <span>
						<a href="/pageelements/rank/mod_id/103/category_id/0"
                           title="排行榜与促销" class="newTab">
							排行榜与促销						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">问卷调查管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/questionnaire/index"
                           title="问卷调查列表" class="newTab">
							问卷调查列表						</a>
						</span>
                    <span>
						<a href="/fund/index"
                           title="优选基金活动" class="newTab">
							优选基金活动						</a>
						</span>
                    <span>
						<a href="/sms/index"
                           title="短信通知" class="newTab">
							短信通知						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">广告管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/pageelements/manageAd/pageid/1/type/2"
                           title="首页广告" class="newTab">
							首页广告						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/1"
                           title="母婴食品" class="newTab">
							母婴食品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/2"
                           title="营养保健品" class="newTab">
							营养保健品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/3"
                           title="粮油副食" class="newTab">
							粮油副食						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/4"
                           title="酒水饮料" class="newTab">
							酒水饮料						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/5"
                           title="冲调茶饮" class="newTab">
							冲调茶饮						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/6"
                           title="休闲食品" class="newTab">
							休闲食品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/7"
                           title="饼干点心" class="newTab">
							饼干点心						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/8"
                           title="生鲜日配" class="newTab">
							生鲜日配						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/9"
                           title="美食用品" class="newTab">
							美食用品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/243/type/2"
                           title="商品列表页面" class="newTab">
							商品列表页面						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/375/type/2"
                           title="美食地图" class="newTab">
							美食地图						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/389"
                           title="礼品中心" class="newTab">
							礼品中心						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/2/cid/452"
                           title="时令优选" class="newTab">
							时令优选						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">商品展示</a>
            <ul>
                <li>
			            						<span>
						<a href="/pageelements/manageAd/pageid/1/type/3"
                           title="首页商品" class="newTab">
							首页商品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/1"
                           title="母婴食品" class="newTab">
							母婴食品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/2"
                           title="营养保健品页" class="newTab">
							营养保健品页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/3"
                           title="粮油副食页" class="newTab">
							粮油副食页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/4"
                           title="酒水饮料页" class="newTab">
							酒水饮料页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/5"
                           title="冲调茶饮页" class="newTab">
							冲调茶饮页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/6"
                           title="休闲食品" class="newTab">
							休闲食品						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/7"
                           title="饼干点心" class="newTab">
							饼干点心						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/8"
                           title="生鲜日配页" class="newTab">
							生鲜日配页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/9"
                           title="美食用品页" class="newTab">
							美食用品页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/243/type/3"
                           title="列表页面页" class="newTab">
							列表页面页						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/375/type/3"
                           title="美食地图" class="newTab">
							美食地图						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/389"
                           title="礼品中心" class="newTab">
							礼品中心						</a>
						</span>
                    <span>
						<a href="/pageelements/manageAd/pageid/95/type/3/cid/452"
                           title="时令优选" class="newTab">
							时令优选						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">活动页面管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/activity/index"
                           title="活动页面管理" class="newTab">
							活动页面管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">栏目分配</a>
            <ul>
                <li>
			            						<span>
						<a href="/pm/index"
                           title="栏目分配" class="newTab">
							栏目分配						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">移动端页面</a>
            <ul>
                <li>
			            						<span>
						<a href="/iosfocus/index"
                           title="焦点图管理" class="newTab">
							焦点图管理						</a>
						</span>
                    <span>
						<a href="/iosColumn/index"
                           title="栏目管理" class="newTab">
							栏目管理						</a>
						</span>
                    <span>
						<a href="/iosArticle/index"
                           title="文章管理" class="newTab">
							文章管理						</a>
						</span>
                    <span>
						<a href="/iosZtManage/index"
                           title="专题管理" class="newTab">
							专题管理						</a>
						</span>
                    <span>
						<a href="/mobileActivity/index"
                           title="活动管理" class="newTab">
							活动管理						</a>
						</span>
                    <span>
						<a href="/mobileUpgrade/index"
                           title="升级管理" class="newTab">
							升级管理						</a>
						</span>
                    <span>
						<a href="/mobileMovement/index"
                           title="推送管理" class="newTab">
							推送管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">活动专题管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/partake/index"
                           title="小吃街活动" class="newTab">
							小吃街活动						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">优选基金管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/fund/add"
                           title="增加优选基金" class="newTab">
							增加优选基金						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">首页管理-新版</a>
            <ul>
                <li>
			            						<span>
						<a href="/adsFloor/index"
                           title="楼层管理" class="newTab">
							楼层管理						</a>
						</span>
                    <span>
						<a href="/adsData/index"
                           title="广告管理" class="newTab">
							广告管理						</a>
						</span>
                    <span>
						<a href="/adsSale/index"
                           title="促销管理" class="newTab">
							促销管理						</a>
						</span>
                    <span>
						<a href="/html/indexmake"
                           title="预览生成" class="newTab">
							预览生成						</a>
						</span>
                    <span>
						<a href="/mainpageArticle/index"
                           title="首页文章列表" class="newTab">
							首页文章列表						</a>
						</span>
                    <span>
						<a href="/adsModels/index"
                           title="广告位置管理" class="newTab">
							广告位置管理						</a>
						</span>

                </li>
            </ul>
        </li>

        <li class="leftMenu menu_14">
            <a class="parent" href="javascript:void(0);">公共广告管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/adsCateAds/index"
                           title="广告管理" class="newTab">
							广告管理						</a>
						</span>
                    <span>
						<a href="/adsCateBrand/index"
                           title="品牌管理" class="newTab">
							品牌管理						</a>
						</span>
                    <span>
						<a href="/adsCateActivity/index"
                           title="活动链接管理" class="newTab">
							活动链接管理						</a>
						</span>
                    <span>
						<a href="/adsCateSecond/index"
                           title="JS导航二级分类" class="newTab">
							JS导航二级分类						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_17">
            <a class="parent" href="javascript:void(0);">采购报表管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/report/purchase/newReceiving"
                           title="收货报表" class="newTab">
							收货报表						</a>
						</span>
                    <span>
						<a href="/report/purchase/newReturn"
                           title="退货报表" class="newTab">
							退货报表						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_21">
            <a class="parent" href="javascript:void(0);">CORP工具箱</a>
            <ul>
                <li>
			          <span>
						<a href="/tools/warehouseAuto/index"
                           title="新增仓库初始化数据" class="newTab">
							新增仓库初始化数据						</a>
						</span>
                    <span>
						<a href="/tools/product/index"
                           title="商品模块" class="newTab">
							商品模块						</a>
						</span>
                    <span>
						<a href="/tools/productQueueStock/index"
                           title="订单模块" class="newTab">
							订单模块						</a>
						</span>
                    <span>
						<a href="/tools/favourable/index"
                           title="优惠活动模块" class="newTab">
							优惠活动模块						</a>
						</span>
                    <span>
						<a href="/tools/finance/cannelInvoice"
                           title="供应商结算税票撤回" class="newTab">
							供应商结算税票撤回						</a>
						</span>

                </li>
            </ul>
        </li>


        <li class="leftMenu menu_32">
            <a class="parent" href="javascript:void(0);">权限管理</a>
            <ul>
                <li>
			            						<span>
						<a href="/auth/qx/outExcelQx"
                           title="导出权限" class="newTab">
							导出权限						</a>
						</span>
                    <span>
						<a href="/auth/qx/getQx"
                           title="申请权限" class="newTab">
							申请权限						</a>
						</span>

                </li>
            </ul>
        </li>
    </ul>
</div>
<div region="center" style="background:#f1f1f1;padding:12px 12px 12px 4px;">
    <div class="easyui-tabs" fit="true" border="false" id="tab" ></div>
</div>
</body>
</html>