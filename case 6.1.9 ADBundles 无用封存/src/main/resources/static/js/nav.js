$(document).ready(function(e) {

	$("#bd").height($(window).height() - $("#ft").height());
	$("iframe").height($("#bd").height() - $(".tab").height()-8);
	
	$(top.window).resize(function(e) {
        $("#bd").height($(window).height() - $("#ft").height());
		$("iframe").height($("#bd").height() - $(".tab").height()-8);
    });
	$(".tab").on("click","li",function(){
		$(this).addClass("current").siblings().removeClass("current");
	});
	
	$(".sidebar-hide").click(function(){
		$(".sidebar").hide();
		$("#bd").css("padding-left","0");
		$(".sidebar-show").show();
		$.each($('iframe'), function(){
			if($(this)[0].contentWindow.resizeWidth){
				$(this)[0].contentWindow.resizeWidth();	
			}
		});
		
	});
	$(".sidebar-show").click(function(){
		$(".sidebar").show();
		$("#bd").css("padding-left","203px");
		$(this).hide();
		$.each($('iframe'), function(){
			if($(this)[0].contentWindow.resizeWidth){
				$(this)[0].contentWindow.resizeWidth();	
			}
		});
	});

});