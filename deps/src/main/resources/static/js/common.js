/**
 * 
 */
$.custom = {
	url: {
		user : 'http://127.0.0.1:9010/',
		files: 'http://127.0.0.1:9006/',
		
	},
	
	getUrlParam : function(name) {
		var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
		var r = window.location.search.substr(1).match(reg);
		console.info(r);
		if (r != null)
			return decodeURI(r[2]);
		return null;
	},

	ajax : function(data) {
		var sessionId = $.custom.getUrlParam('sessionId');
		if(!sessionId){
			location.href = $.custom.url.user + 'user/login?callback='+ encodeURI(location.href);
			return ;
		}
		if(data.url.indexOf('?') != -1)
			data.url += '&sessionId=' + sessionId;
		else
			data.url += '?sessionId=' + sessionId;
		$.ajax({
			method : data.method || 'GET',
			url : data.url,
			async : data.async || false,
			success : function(result) {
				console.info(result);
				data.success(result);
			},
			error : function(error) {
				console.error(error);
			}
		});
	}
}