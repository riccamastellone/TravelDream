$(document).ready(function($) {
	function onBgresize() {
		var $gfwidth = window.innerWidth;
		var $gfheight = window.innerHeight;

		var $loginw = $('.login-wrap').width();
		var $loginh = $('.login-wrap').height();

		$('.login-fullwidith').css({
			'width' : $gfwidth + 'px',
			'height' : $gfheight + 'px'
		});
		
		if($('.register').size()>0) {
			$('.login-wrap').css({
				'margin-left' : $gfwidth / 2 - $loginw / 2 + 'px',
				'margin-top' : '10px'
			});
		} else {
			$('.login-wrap').css({
				'margin-left' : $gfwidth / 2 - $loginw / 2 + 'px',
				'margin-top' : $gfheight / 2 - $loginh / 2 + 'px'
			});
		}
		

	}
	onBgresize();
	$(window).resize(function() {
		onBgresize();
	});

	$('#btn-signin').click(function() {
		$.ajax({
			type : "POST",
			url : "j_security_check",
			data : {
				j_username : $('input[name=j_username]').val(),
				j_password : $('input[name=j_password]').val()
			},
			success : function(data, textStatus, xhr) {
				if(data=='<?xml version=\'1.0\' encoding=\'UTF-8\' ?>\n<!DOCTYPE html>\n<html xmlns="http://www.w3.org/1999/xhtml">\nerror\n</html>') {
					errorMessage();
				} else {
					window.location = "store/";
				}
				
			},
			error : function(jqXHR, textStatus, errorThrown) {
				errorMessage();
			}

		});
	});

});