$(function() {
	bindEvent(); //绑定事件
	load('/'); //加载目录文件

	function load(path) {
		var filesInfo = getDirInfo(path);
		if (!filesInfo || filesInfo.respCode != '0000') {
			return;
		}
		$('#largePic_detail').attr('meta', JSON.stringify(filesInfo));

		loadDirectoryNavigation(filesInfo.data.currentPath);// 加载导航头
		loadDetail(filesInfo);// 加载文件夹、文件信息
	}

	function loadDetail(filesInfo) {
		var detailHtml = null;
		// 判断展示方式 大图/列表
		if ($('#show_type').hasClass('largePic'))
			detailHtml = template('largePicTemp', filesInfo.data);// 大图
		else
			detailHtml = template('detailTemp', filesInfo.data); // 列表

		$('#largePic_detail').html(detailHtml);
	}

	/** 加载目录头, 传入当前目录 */
	function loadDirectoryNavigation(path) {
		if (!path)
			return;
		$('#directory_navigation').attr('meta', path);
		var array = path.split('/');
		var dirNav = [];
		for ( var i in array) {
			if (array[i] == '' && i != 0)
				continue;
			var uri = '/';
			if (i != 0)
				for (var j = 1; j <= i; j++)
					uri = uri + array[j] + (j != i ? '/' : '');
			dirNav.push({
				name : array[i] ? array[i] : 'ALL',
				uri : uri
			});
		}
		var dirNavData = {
			data : dirNav
		};
		var dirNavHtml = template('directoryNavigationTemp', dirNavData);
		$('#directory_navigation').html(dirNavHtml);
	}

	/** 根据目录获取文件信息 path: 指定目录以/ 开始 */
	function getDirInfo(path) {
		var resultData;
		$.custom.ajax({
			url : 'fileDir' + path,
			success : function(result) {resultData = result;}
		});
		return resultData;
	}

	function bindEvent() {
		//光标退出,保存文件夹
		$('#largePic_detail').on('blur', '#new_folder_input', function(){
			if($(this).val() == ''){
				$(this).parent().parent().remove();
				$('#new_folder').attr('meta', false);
				return ;
			}
			var currentDir = $('#directory_navigation').attr('meta');
			var targetUri = 'fileDir' + currentDir;
			var index = currentDir.lastIndexOf("/");
			if (index != currentDir.length - 1)
				targetUri += '/';
			targetUri += $(this).val();
			
			$.custom.ajax({
				method : 'POST',
				url : targetUri ,
				success : function(result) {
					$('#new_folder').attr('meta', false);
					if(result.respCode == '0000'){
						load(currentDir);
					}else{
						$('#new_folder_input').parent().parent().remove();
						layer.msg(result.respMsg);
					}
				}
			});
		});
		
		//新建文件夹
		$('#new_folder').on('click', function(){
			if($(this).attr('meta') == 'true') return;
			$(this).attr('meta', true);
			
			if($('#show_type').hasClass('list')){ //列表模式
				var context = '<tr class="item">';
				context += '<td> <img src= "img/file_iocn/FOLDER.png" /> <input id="new_folder_input" placeholder="新建文件夹" type="text" style="padding: .38rem 1.0rem;height: 2.2em; width: 10em; margin:0;"/> </td>';
				context += '<td> - - </td>';
				context += '<td>文件夹</td>';
				context += '</tr>';
				$('#largePic_detail tbody').before(context);
				
			} else { //大图模式-添加文件夹
				var context = '<div class="item largePicItem"> ';
				context += '<div style="text-align: center; margin-top: 0.5em;"><img src="img/file_iocn/FOLDER.png"></div>';
				context += '<div class="filename"><input id="new_folder_input" placeholder="新建文件夹" type="text" style="padding: .38rem 1.0rem;height: 2.2em; width: 8em; margin:0;"/></div>';
				context += '</div>';
				$($('#largePic_detail div')[0]).before(context);
			}
			$('#new_folder_input').focus();
			
		});
		
		
		// 提交上传文件
		$('#largePic_detail').on('click', '#submit_file', function(){
		    var formData = new FormData();
		    var files = $('#files_hide_list input');
		    for(var i = 0; i < files.length; i++)
		        formData.append("file", files[i].files[0]);
		    
		    $.custom.ajax({
	            type: 'POST',
	            url: '/files' + $('#directory_navigation').attr('meta'),
	            data: formData,
	            processData: false,
	            contentType: false,
	            success: function (msg) {
	                load($('#directory_navigation').attr('meta'));
	            },
	        });
		});
		
		
		//重新选着文件
		$('#largePic_detail').on('click', '.file', function() {
			var his = $(this);
			var id = his.attr('file-id');
			$('#' + id).click();
		});

		// 添加文件完成.界面展示文件
		$('#largePic_detail').on( 'change', "input", function() {
			var his = $(this);
			var filePath = his.val();
			var id = his.attr('id');
			if (!filePath) {
				$('#' + id).remove();
				$('[file-id="' + id + '"]').remove();
				return;
			}

			var fileName = filePath.substr(filePath
					.lastIndexOf('\\') + 1);
			if ($('[file-id="' + id + '"]').length == 0) {
				var divContext = '<div class="largePicItem file" file-id="' + id + '" >';
				divContext += '<div style="text-align: center; margin-top: 0.5em;"><img src="img/file.png"></div>';
				divContext += '<div class="filename">' + fileName + '</div>';
				$('#files_show_list').prepend(divContext);
			} else {
				$('[file-id="' + id + '"]').find('.filename').html(fileName);
			}
		});

		// 添加需要上传的文件
		$('#largePic_detail').on( 'click', '#add_file', function() {
			var id = getuuid();
			var fileInputContext = "<input type='file' id='" + id + "' accept='.*'/>";
			$("#files_hide_list").append(fileInputContext);
			$('#' + id).click();
		});

		// 关闭上传文件
		$('#largePic_detail').on('click', '#close_file', function() {
			load($('#directory_navigation').attr('meta'));
		});

		// 目录头点击事件
		$('#directory_navigation').on('click', 'span', function() {
			var $this = $(this);
			if ($this.hasClass('no'))
				return;
			load($this.attr('meta'));
		});
		// 打开上传文件
		$('#upload_file').on('click', function() {
			$('#largePic_detail').html(template('uploadTemp'));
		});

		// 修改展示类型
		$('#show_type').on('click', function() {
			var $this = $(this);
			var src;
			var css;
			if ($this.hasClass('list')) {
				$this.addClass('largePic').removeClass('list');
				src = 'img/list.png';
				$this.attr('title', '列表');
				css = {
					width : '2.6em'
				};
			} else {
				$this.removeClass('largePic').addClass('list');
				src = 'img/largePic.png';
				$this.attr('title', '大图');
				css = {
					width : '4em'
				};
			}
			$('#show_type img').attr("src", src).css(css);
			var filesInfo = JSON.parse($('#largePic_detail').attr('meta'));
			loadDetail(filesInfo); // 加载明细
		});

		$('#largePic_detail').on('click', '.item', function() {
			var $this = $(this);
			var currentDir = $('#directory_navigation').attr('meta');
			var targetName = $this.attr('meta');
			var targetUri = currentDir;
			// 文件夹
			if ($this.hasClass('isFolder')) {
				var index = currentDir.lastIndexOf("/");
				if (index != currentDir.length - 1)
					targetUri += '/';
				targetUri += targetName;
				load(targetUri);
			} else if ($this.hasClass('isFile')) { // 文件

				// 文件下载
				location.href = '/files/' + $this.attr('token');
			} else {
				console.error('click not file and not folder !!');
			}
		});
	}

});

// 获取一个唯一的ID
function getuuid() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}