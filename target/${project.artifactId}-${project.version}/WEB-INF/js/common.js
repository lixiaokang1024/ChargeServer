// JavaScript Document
var SF_STATIC_BASE_URL = '';
(function(window) {
	var document = window.document,
		alert = window.alert,
		confirm = window.confirm
		$ = window.jQuery;
	var SF = {
		Config: {},
		Widget: {},
		App: {},
		Static: {}
	};

		SF.loadJs = function(sid, callback, dequeue) {
		SF.loadJs.loaded = SF.loadJs.loaded || {};
		SF.loadJs.packages = SF.loadJs.packages || {
			'jquery.thickbox': {
				'js': [SF_STATIC_BASE_URL + '/js/jquery/jquery.thickbox.js'],
				'check': function() {
					return !!window.tb_show;
				}
			},
			'jquery.select': {
				'js': [SF_STATIC_BASE_URL + '/js/jquery/jquery.select.js'],
				'check': function() {
					return !!$.fn.relateSelect;
				}
			},
			'data.city': {
				'js': [SF_STATIC_BASE_URL + '/js/data/region_data.js'],
				'depends': ['jquery.select'],
				'check': function() {
					return !!window.REGION_DATA;
				}
			},
			'data.category': {
				'js': ['/cate/category/'],
				'depends': ['jquery.select'],
				'check': function() {
					return !!window.CATEGORY;
				}
			}
		};

		if (!dequeue) {
			$(window).queue('loadJs', function() {
				SF.loadJs(sid, callback, true);
			});
			$(window).queue('loadJsDone', function(){
				$(window).dequeue('loadJs');
			});
			if ($(window).queue('loadJsDone').length == 1) {
				$(window).dequeue('loadJs');
			}
			return;
		}

		function collect(sid) {
			var jsCollect =[], packages = SF.loadJs.packages[sid], i, l;
			if (packages) {
				if (packages.depends) {
					l = packages.depends.length;
					for (i = 0; i < l; i++) {
						jsCollect = jsCollect.concat(collect(packages.depends[i]));
					}
				}

				if ($.isFunction(packages.check) && !packages.check()) {
					jsCollect = jsCollect.concat(packages.js);
				}
			}

			return jsCollect;
		}

		function load(url) {
			return jQuery.ajax({
				crossDomain: true,
				cache: true,
				type: "GET",
				url: url,
				dataType: "script",
				async: true,
				scriptCharset: "UTF-8"
			});
		}

		var js = collect(sid), deferreds = [], l = js.length, i;
		for (i = 0; i < l; i++) {
			deferreds.push(load(js[i]));
		}
		$.when.apply($, deferreds).then(function() {
			$(window).dequeue('loadJsDone');
			$.isFunction(callback) && callback.call(document);
		}, function() {
			$(window).dequeue('loadJsDone');
		})
	};

	SF.t = function(code) {
		if (window.MSG && window.MSG[code]) {
			return window.MSG[code];
		}

		return code;
	};

	SF.Widget = {
		// 下拉菜单显隐
		pop: function(s) {
			if ($(s).data('SF_BIND_POP')) {
				return;
			}

			var $c = $(s),
				setting = $c.data('pop') || {};
			$c.bind({
				mouseover: function(e) {
					if (setting.pop) {
						$(setting.pop, $c).show();
					}
					if (setting.icon && setting.iconClass) {
						$(setting.icon, $c).addClass(setting.iconClass);
					}
				},
				mouseout: function(e) {
					if (setting.pop) {
						$(setting.pop, $c).hide();
					}
					if (setting.icon && setting.iconClass) {
						$(setting.icon, $c).removeClass(setting.iconClass);
					}
				}

			});

			$c.data('SF_BIND_POP', true);
			$c.triggerHandler('mouseover');
			return;
		},

		// 打开 thickbox 遮罩层
		tbOpen: function(caption, url, imageGroup) {
			function show() {
				window.tb_show(caption, url, imageGroup);
			}

			SF.loadJs('jquery.thickbox', show);
		},

		// 关闭 thickbox 遮罩层
		tbClose: function() {
			window.tb_remove();
		},

		// 用户登陆层
		login: function(backurl, reload) {
			var url;
			backurl = (typeof(backurl) === 'undefined' || !backurl) ? window.location.href : backurl;
			reload = (typeof(reload) === 'undefined') ? true : reload;
			url = '/login/ajax/?' + ($.param({url : backurl, reload: Number(reload)})) + '&TB_iframe&height=228&width=402';
			SF.Widget.tbOpen('<strong>顺丰商城</strong>', url, 'scrolling=no');
		},

		// 分类联动
		category: function(s, options) {
			function relateSelect() {
				var defaults = {
					data: window.CATEGORY
				};
				$(s).relateSelect($.extend(defaults, options || {}));
			}

			SF.loadJs('data.category', relateSelect);
		},

		// 省市联动
		city: function(s, options) {
			function relateSelect() {
				var defaults = {
					data: window.REGION_DATA
				};
				$(s).relateSelect($.extend(defaults, options || {}));
			}
			SF.loadJs('data.city', relateSelect);
		},


		//添加class
		addClass:function(s,onClass){
			$(s).hover(function(){
				$(this).addClass(onClass);
			},function(){
				$(this).removeClass(onClass);
			});
		},
		//搜索框默认值
		tipTxt: function(name){
			$(name).each(function(){
				var oldVal = $(this).val();
				$(this).css({"color":"#888"})
				.focus(function(){
					if($(this).val()!=oldVal){$(this).css({"color":"#000"})}else{$(this).val("").css({"color":"#888"})}
				})
				.blur(function(){
					if($(this).val()==""){$(this).val(oldVal).css({"color":"#888"})}
				})
				.keydown(function(){
					$(this).css({"color":"#000"})
				})
			})
		},
		// 标签切换
		tabs: function(s, e) {
			e = e || "mouseover";
			$(function() {
				$(s).bind(e, function(e) {
					if (e.target === this){
						var tabs = $(this).parent().parent().children("li");
						var panels = $(this).parent().parent().parent().children(".SF-tabs-box");
						var index = $.inArray(this, $(this).parent().parent().find("a"));
						if (panels.eq(index)[0]) {
							tabs.removeClass("SF-tabs-hover");
							tabs.eq(index).addClass("SF-tabs-hover");
							panels.addClass("SF-tabs-hide");
							panels.eq(index).removeClass("SF-tabs-hide");
						}
					}
				});
			});
		},
		Subtr:function(arg1,arg2){
			var r1,r2,m,n;
			try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
			try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
			m=Math.pow(10,Math.max(r1,r2));
			n=(r1>=r2)?r1:r2;
			return ((arg1*m-arg2*m)/m).toFixed(n);
		},
		Add:function(arg1,arg2){
			var r1,r2,m;
			try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
			try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
			m=Math.pow(10,Math.max(r1,r2))
			return (arg1*m+arg2*m)/m
		},
		Acc:function(arg1,arg2){
			var t1=0,t2=0,r1,r2;
			try{t1=arg1.toString().split(".")[1].length}catch(e){}
			try{t2=arg2.toString().split(".")[1].length}catch(e){}
			with(Math){
				r1=Number(arg1.toString().replace(".",""))
				r2=Number(arg2.toString().replace(".",""))
				return (r1/r2)*pow(10,t2-t1);
			}
		},
		Mul:function(arg1,arg2)
		{
			var m=0,s1=arg1.toString(),s2=arg2.toString();
			try{m+=s1.split(".")[1].length}catch(e){}
			try{m+=s2.split(".")[1].length}catch(e){}
			return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
		},
		//日期选择器
		datepicker: function(o) {
			$(o).datepicker({
				dateFormat: 'yy-mm-dd',
				monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				dayNamesMin: ['日','一','二','三','四','五','六']
			});
		},
		strCount:function(str)
		{
			var byteLen = 0;
			var strLen  = str.length;
			if(strLen)
			{
				for(var i = 0; i < strLen; i++)
				{
					if(str.charCodeAt(i)>255)
						byteLen += 1;
					else
						byteLen += 0.5;	//0.5不存在精度问题
				}
			}
			return byteLen;
		},
		refreshOrder:function(order_id, html)
		{
			$('#order_' + order_id).replaceWith(html);
			var location = window.location.href;
			if (location.match(/order\/list/g))
			{
				// todo nothing
			}
			else
			{
				window.location.reload();
			}
		},
		checkTextarea:function(chkname,titname,maxnum){
			$(chkname).keyup(function(){
				var flTxt = Math.floor(maxnum-SF.Widget.txtLength(chkname));
				$(titname).html("您还可以输入"+flTxt+"个字");
				if(flTxt < 0){
					$(titname).html("<div style='color:#FF6600;'>您输入的字数已超出范围，不可以再输入</div>");
				}
			})
		},
		txtLength:function(chkname){
			var getTextarea = $(chkname).val();
			var firstLength = 0;
			for(var i=0;i<getTextarea.length;i++){
				var rs = SF.Widget.GetContentLanguage(getTextarea.substring(i,i+1));
				if(rs == "en"){
					firstLength += 0.5;
				}else{
					firstLength += 1;
				}
			}
			return firstLength;
		},
		checkEmail:function(str) {
			return str.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1;
		},
		GetContentLanguage:function(content){
			var rex;
			rex=content.charCodeAt();
			if (rex<=127) {
				return "en";
			}
		}
	};

	SF.App = {
		topSearch: function(s) {
			if ($(s).data('SF_BIND_FOCUS')) {
				return;
			}

			var $e = $(s);
			$e.bind({
				'focusin': function(e) {
					$e.removeClass('search_goods');
				},
				'focusout': function(e) {
					if ($.trim($e.val()) === '') {
						$e.addClass('search_goods');
					}
				}
			});
			$e.data('SF_BIND_FOCUS', true);
			$e.triggerHandler('focusin');
			return;
		}
	};


	window.SF = SF;
}(window));

$(document).ready(function(){
	//全选
	$("#chkAll").click(
	  function(){
		if(this.checked){
			$("input[name='id[]']").attr('checked', true)
		}else{
			$("input[name='id[]']").not('.readonly').attr('checked', false)
		}
	  }
	);
	//批量删除
	$("#button_delete").click(function(){
		if(!confirm('确定删除吗？')) return false;
	});
	//搜索提交
	$("#advanced_search_botton").click(function(){
		$("#advanced_search").submit();
	});
	
	//确认提示
	$(".confirm").live('click',function(){
		var url = $(this).attr('href');
		var title = $(this).attr('title');
		$.messager.confirm('系统信息','确定'+title+'吗?',function(r){
			if(r)
			{
				goTo(url);
			}
		});
		return false;
	});

	//给列表页加上鼠标经过效果
	$('table.list tr').hover(
		function(){$(this).addClass('hover');},
		function(){$(this).removeClass('hover');}
	);

	//错误提示信息
	var msg = $.cookie('errorMessage');
	if(msg != null && msg != ''){
		$.messager.alert('系统消息',msg,'error');
		$.cookie('errorMessage',null,{path:'/'});
	}

	//成功提示信息
	var msg = $.cookie('successMessage');
	if(msg != null && msg != ''){
		$.messager.alert('系统消息',msg,'info');
		$.cookie('successMessage',null,{path:'/'});
	}

	$.extend({
		//弹出窗口
	    dialog: function(options){
			if(typeof($("#myDialog")[0]) == "undefined"){
				$("body").prepend("<div id='myDialog'></div>");
			}
			$('#myDialog').html("<iframe id='myDialogIframe' name='"+options.name+"' src='"+options.url+"' scrolling='no' frameborder='0' width='"+
					options.width+"' height='"+options.height+"' />");
			options.width = options.width + 15;
			options.height = options.height + 35;
			$('#myDialog').window(options);
		}
	});

	//打开一个新Tab
	$('a.newTab').live('click',function(){
		addTab($(this).attr('title'),$(this).attr('href'),true);
		return false;
	})

	//关闭当前Tab
	$('#closeTab').live('click',function(){
		if(parent.window == window) {
			window.close();
			return false;
		}

		var pp = parent.$('#tab').tabs('getSelected');
		var title = pp.panel('options').title;
		parent.$('#tab').tabs('close', title);
	});

	//修复combobox和validateform验证冲突的问题
	$('.combobox').combobox({editable:false, onSelect:function(){
			$(this).val($(this).combobox('getValue'));
		}
	});
	
	//EasyUI DataGrid 隐藏上次隐藏的列
	hideLastColumnMenu();

	//EasyUI DataGrid 高度自适应
	$('.easyui-datagrid').datagrid('resize', {
		height:$(document).height()
	});
	
	//给数组添加删除方法
	Array.prototype.remove=function(dx) 
	{ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    for(var i=0,n=0;i<this.length;i++) 
	    { 
	        if(this[i]!=this[dx]) 
	        { 
	            this[n++]=this[i] 
	        } 
	    } 
	    this.length-=1 
	} 
});

//生成EasyUI DataGrid 顶部右键菜单
function createColumnMenu(){
	var ck = $.cookie(location.href);
	if(ck == null || ck == '')
		var arr = new Array();
	else
		var arr = ck.split(',');
	
	var tmenu = $('<div id="tmenu" style="width:100px;"></div>').appendTo('body');
	var fields = $('#datagrid').datagrid('getColumnFields');
	for(var i=0; i<fields.length; i++){
		if(inArray(arr,fields[i]))
			icon = 'icon-empty';
		else
			icon = 'icon-ok';
		
		var option = $('#datagrid').datagrid('getColumnOption',fields[i]);
		$('<div iconCls="'+icon+'" id="tmenu_field_'+fields[i]+'" />').html(option.title).appendTo(tmenu);
	}
	tmenu.menu({
		onClick: function(item){
			var field = item.id.replace('tmenu_field_','');
			if (item.iconCls=='icon-ok'){
				$('#datagrid').datagrid('hideColumn', field);
				rememberColumnMenu(field,'hide');
				tmenu.menu('setIcon', {
					target: item.target,
					iconCls: 'icon-empty'
				});
			} else {
				$('#datagrid').datagrid('showColumn', field);
				rememberColumnMenu(field,'show');
				tmenu.menu('setIcon', {
					target: item.target,
					iconCls: 'icon-ok'
				});
			}
		}
	});
}

//使用cookie记住列隐藏状态
function rememberColumnMenu(field,status){
	var ck = $.cookie(location.href);
	if(ck == null || ck == '')
		var arr = new Array();
	else
		var arr = ck.split(',');
	if(status == 'show'){
		for(i in arr){
			if(arr[i]==field)
				arr.remove(i);
		}
	}
	else if(status == 'hide'){
		if(inArray(arr,field) == false)
			arr.push(field)
	}
	$.cookie(location.href,arr.toString());
}
//使用cookie记住列隐藏状态
function hideLastColumnMenu(){
	var ck = $.cookie(location.href);
	if(ck == null || ck == '')
		return false;
	var arr = ck.split(',');
	for(i in arr){
		$('#datagrid').datagrid('hideColumn', arr[i]);
	}
}

//手动设置输入框的控制效果（长度）
function checklength(obj,t,l){
	if(t=='num'){
		$(obj).val($(obj).val().replace(/^0|\D/g, "")) ;
	}
	if($(obj).val().length>=l){
		 $(obj).val($(obj).val().substring(0,l));
	}
}

//表单重置扩展
jQuery.fn.reset = function () {
	$(this).each (function() { this.reset(); });
}

function inArray(a, obj) {
    for (var i = 0; i < a.length; i++) {
        if (a[i] === obj) {
            return true;
        }
    }
    return false;
}

/**
 * flexigrid重置，主要是用户搜索后返回
 * 需要两个条件：1搜索框ID是advanced_search，2 flexigrid的ID是 flexigrid
 * @param com
 * @param grid
 */
function resetGrid(com, grid) {
	delCookie('params');
	setCookie ('_CLICK_ALL',1,0,'/','','');
	$('#advanced_search').reset();
	$("#flexigrid").flexReload();
}

/**
 * 修复WdatePicker选择日期后不验证的bug
 * 调用方式 WdatePicker({onpicked:fixDatePicker})
 */

function fixDatePicker(){
	this.focus();this.blur();
	/*var obj = document.getElementById("_my97DP");
	//时间插件的div ID为_my97DP， 当点击完后隐藏该div
	//该插件默认在顶级body（比如包含多个frameset框架）下自动生成_my97DP
	obj = obj?obj:parent.document.getElementById("_my97DP");
	$(obj).css({"display":"none"});*/
}

/**
 * 页面跳转，为了解决IE下 location.href 没有HTTP_REFERER的问题
 * @author zhangliping
 * @param url 要跳转的URL
 */
function goTo(url) {
	var a = document.createElement("a");
	if(!a.click) {
		window.location = url;
		return;
	}
	a.setAttribute("href", url);
	a.style.display = "none";
	$("body").append(a);
	a.click();
}

function isEmpty(str)
{
	if(str != null && str.length > 0)
	{
		return true;
	}
	return false;
}

//验证邮箱
function is_email(email){
	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	var str = "";
	str = !reg.test(email) ? false : true;
	return str;
}

//验证是否是数字
function checkNum(obj)
{
	var re = /^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/;
     if (!re.test(obj))
     {
        return false;
     }else{
		return true;
	 }
}

function provinceFun(id) {

	var cityHtml = document.getElementById('cityHtml');

	cityHtml.innerHTML = '';

	if(id == -1) {
		return false;
	} else {
		url=""+ROOTDIR+"/ajax/getregionChild/id/"+id+"";

		$.ajax({
			url: url,
			type: "GET",
			dataType:"html",
			beforeSend: function(){

			},
			success: function(msg){
				var obj = JSON.parse(msg);
				var content = '<select name="city" id="city" onchange="cityFun(this.value)">';
					content += '<option value="-1">请选择</option>';
				for(var i in obj) {
					content += '<option value="'+obj[i]['region_id']+'">'+obj[i]['region_name']+'</option>';
				}
				content += '</select>';

				cityHtml.innerHTML = content;
			}
		})
	}
}

function cityFun(id) {

	var districtHtml = document.getElementById('districtHtml');

	if(id == -1) {
		districtHtml.innerHTML = '';
	}
	else {


		url=""+ROOTDIR+"/ajax/getregionChild/id/"+id+"";

		$.ajax({
			url: url,
			type: "GET",
			dataType:"html",
			beforeSend: function(){

			},
			success: function(msg){
				var obj = JSON.parse(msg);
				var content = '<select name="district" id="district">';
				for(var i in obj) {
					content += '<option value="'+obj[i]['region_id']+'">'+obj[i]['region_name']+'</option>';
				}
				content += '</select>';

				districtHtml.innerHTML = content;
			}
		})
	}
}

function getCookie (name) {
 var arg = name + "=";
 var alen = arg.length;
 var clen = document.cookie.length;
 var i = 0;
 while (i < clen) {
  var j = i + alen;
  if (document.cookie.substring(i, j) == arg) return getCookieVal (j);
  i = document.cookie.indexOf(" ", i) + 1;
  if (i == 0) break;
 }
 return null;
}

function setCookie(name, value, expires, path, domain, secure)
{
  var today = new Date();
  var expiry = new Date(today.getTime() + 100000 * 24 * 60 * 60 * 1000);
  if(expires==''||expires==null)
  {
 	expires=expiry;
  }
  var curCookie = name + "=" + escape(value) +
      ((expires) ? "; expires=" + expires.toGMTString() : "") +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      ((secure) ? "; secure" : "");
  document.cookie = curCookie;

}

function delCookie(name) {
 expdate = new Date();
 expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
 setCookie(name, "", "", "/", "", "");
}


var expdate= new Date();

function getCookieVal (offset) {
 var endstr = document.cookie.indexOf (";", offset);
 if (endstr == -1) endstr = document.cookie.length;
 return unescape(document.cookie.substring(offset, endstr));
}

//时间格式转换成时间戳
//转换时间格式  2011-11-11 11:11:11
function unixDate(data){
	data = data+" 00:00:00";
	var new_str = data.replace(/:/g,'-');
	new_str = new_str.replace(/ /g,'-');
	var arr = new_str.split("-");
	var timen = new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4],arr[5]));
	return parseInt((timen.getTime())/1000);//PHP计算时间戳
}

//限制input只能输入数字和小数点
//<input id="" onkeyup="clearNoNum(this)" />
function clearNoNum(obj){
    obj.value = obj.value.replace(/[^\d.]/g,"");
    obj.value = obj.value.replace(/^\./g,"");
    obj.value = obj.value.replace(/\.{2,}/g,".");
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

function addTab(title, url, flag){
	if(parent.window == window) {
		window.open(url, "_blank");
		return false;
	}
	var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	if (parent.$('#tab').tabs('exists', title)){
		parent.$('#tab').tabs('close', title);
	}
	parent.$('#tab').tabs('add',{
		title:title,
		content:content,
		closable:flag
	});
}


var Browser = new Object();

Browser.isMozilla = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined') && (typeof HTMLDocument != 'undefined');
Browser.isIE = window.ActiveXObject ? true : false;
Browser.isFirefox = (navigator.userAgent.toLowerCase().indexOf("firefox") != - 1);
Browser.isSafari = (navigator.userAgent.toLowerCase().indexOf("safari") != - 1);
Browser.isOpera = (navigator.userAgent.toLowerCase().indexOf("opera") != - 1);

var Utils = new Object();

Utils.htmlEncode = function(text)
{
  return text.replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

Utils.trim = function( text )
{
  if (typeof(text) == "string")
  {
    return text.replace(/^\s*|\s*$/g, "");
  }
  else
  {
    return text;
  }
}

Utils.isEmpty = function(val)
{
  switch (typeof(val))
  {
    case 'string':
      return Utils.trim(val).length == 0 ? true : false;
      break;
    case 'number':
      return val == 0;
      break;
    case 'object':
      return val == null;
      break;
    case 'array':
      return val.length == 0;
      break;
    default:
      return true;
  }
}

Utils.isNumber = function(val)
{
  var reg = /^[\d|\.|,]+$/;
  return reg.test(val);
}

Utils.isInt = function(val)
{
  if (val == "")
  {
    return false;
  }
  var reg = /\D+/;
  return !reg.test(val);
}

Utils.isEmail = function( email )
{
  var reg1 = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;

  return reg1.test( email );
}

Utils.isTel = function ( tel )
{
  var reg = /^[\d|\-|\s|\_]+$/; //只允许使用数字-空格等

  return reg.test( tel );
}

Utils.fixEvent = function(e)
{
  var evt = (typeof e == "undefined") ? window.event : e;
  return evt;
}

Utils.srcElement = function(e)
{
  if (typeof e == "undefined") e = window.event;
  var src = document.all ? e.srcElement : e.target;

  return src;
}

Utils.isTime = function(val)
{
  var reg = /^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}$/;

  return reg.test(val);
}

var listTable = new Object;

listTable.query = "query";
listTable.filter = new Object;
listTable.url = location.href.lastIndexOf("?") == -1 ? location.href.substring((location.href.lastIndexOf("/")) + 1) : location.href.substring((location.href.lastIndexOf("/")) + 1, location.href.lastIndexOf("?"));


/**
 * 创建一个可编辑区
 */
listTable.edit = function(obj, act, id)
{
  var tag = obj.firstChild.tagName;
  if (typeof(tag) != "undefined" && tag.toLowerCase() == "input")
  {
    return;
  }

  /* 保存原始的内容 */
  var org = Utils.trim(obj.innerHTML);
  var val = Browser.isIE ? obj.innerText : obj.textContent;

  /* 创建一个输入框 */
  var txt = document.createElement("INPUT");
  txt.value = (val == 'N/A') ? '' : val;
  txt.style.width = (obj.offsetWidth + 12) + "px" ;
	txt.style.border = "1px solid #999" ;

  /* 隐藏对象中的内容，并将输入框加入到对象中 */
  obj.innerHTML = "";
  obj.appendChild(txt);
  txt.focus();

  /* 编辑区输入事件处理函数 */
  txt.onkeypress = function(e)
  {
    var evt = Utils.fixEvent(e);
    var obj = Utils.srcElement(e);

    if (evt.keyCode == 13)
    {
      obj.blur();

      return false;
    }

    if (evt.keyCode == 27)
    {
      obj.parentNode.innerHTML = org;
    }
  }

  /* 编辑区失去焦点的处理函数 */
  txt.onblur = function(e)
  {
  	if(txt.value != val){
	   if (Utils.trim(txt.value).length > 0)
	    {
		   $.getJSON(listTable.url, { 'id': id ,'action':act ,'value':Utils.trim(txt.value)}, function(res){

			   if (!res.status)
			      {
			        	alert(res.message);
			      }

			      obj.innerHTML = res.status ? res.content : org;
			 });
	     // res = myAjax.call(listTable.url, "action="+act+"&val=" + Utils.trim(txt.value) + "&id=" +id, null, "POST", "JSON", false);
	    }else{
		   obj.innerHTML = org;
		}
  	}else{
      obj.innerHTML = org;
    }
  }
}

/*
<textarea id="textarea_num"  cols="80" rows="4" onkeydown='countChar();' onkeyup='countChar();'></textarea>
<p>
    最多字数：<input readonly="true" maxLength="4" id="textarea_total" size="3" value="200"  />
    已用字数：<input readonly="true" maxLength="4" id="textarea_used" size="3" value="0"  />
    剩余字数：<input readonly="true" maxLength="4" id="textarea_remain" size="3" value="200"  />
</p>
 */
function countChar(){
    var max = $('#textarea_total').val();
    var used = $.trim($('#textarea_num').val()).length;
    $('#textarea_used').val(used);
    var remain = max - used;
    if(remain<0){
        var text = $('#textarea_num').val().substr(0, max);
        $('#textarea_num').val(text);
    }else{
        $('#textarea_remain').val(remain);
    }
}

//保留两位小数,金额使用，整数也会保留两位
function toDecimal(x) { 
    var f = parseFloat(x);   
    return f.toFixed(2);   
}   