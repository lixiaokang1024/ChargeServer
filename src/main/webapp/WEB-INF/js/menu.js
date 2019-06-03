$(function(){
	$('#menu li a').click(function(){
		var checkElement = $(this).next();
	    if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
	        return false;
	    }
	    if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
	        $('#menu ul:visible').slideUp('normal');
	        checkElement.slideDown('normal');
	        return false;
	    }
	});
	
	$('#topMenu a').click(function(){
		$('#topMenu a').removeClass('current');
		$(this).addClass('current');
		$('.leftMenu').hide();
		$('.'+$(this).attr('id')).show();
		$('#menu ul').hide();
		$('.'+$(this).attr('id')+' ul:first').show();
	});
	
	$('#topMenu a:first').click();
});