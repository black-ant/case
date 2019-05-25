
(function($) {
	$.uiWidget('ui.Pagination', {
		options : /**@lends uedPagination# */
		{
			totalRecord : 0, //总记录条数
			perPageRecord : 10, // 每页显示多少条记录
			currentPage : 1, //当前页码
			displayStyle:[2,3,2], // 页码数（前，中，末）
			showMessage : true, // 显示记录信息
			showPrevNextBtn :true, // 显示左右箭头标识
			
			callback : function(id) {
				return id;
			}
		},
		_create : function() {
			var $el = this.element, options = this.options, self = this;
			var totalRecord = (options.totalRecord < 0) ? 0 : options.totalRecord;
			var $currrentpage = options.currentPage;
			$el.addClass("ue-clear uew-pagination");
			
			// 当无数据或者数据少于每页的最多记录时的情况,不生成分页
			if (totalRecord == 0 && totalRecord < options.perPageRecord) {}
			else {
				this._createPagination();
			}
			
			// 绑定 直接进入某页面 事件
			/*	$(".setPage", $el).live("click", function(evt) {
				var setPageNo = $(this).parent().find("input[name='setPage']").val();
				if (setPageNo != null && setPageNo != "" && setPageNo > 0 && setPageNo <= $.pageNums) {
					self._pageSelected(setPageNo - 1, evt);
				}
			});*/
		},
		_init : function() {
			var $el = this.element, options = this.options, self = this;
			var totalRecord = (options.totalRecord < 0) ? 0 : options.totalRecord;
			var $currrentpage = options.currentPage;
			$el.addClass("ue-clear uew-pagination");
			
			// 当无数据或者数据少于每页的最多记录时的情况,不生成分页
			if (totalRecord == 0 && totalRecord < options.perPageRecord) {
				// 删除先前生成的DOM节点
				$el.children().remove();
			}
			else {
				this._createPagination();
			}
		},
		_createPagination : function() {
			var $el = this.element, options = this.options, self = this, perPageRecord = (!options.perPageRecord || options.perPageRecord < 0) ? 1 : options.perPageRecord, totalRecord = (options.totalRecord < 0) ? 0 : options.totalRecord, currentPage = options.currentPage -1, st = options.showType;

			// 获取总页数
			this.pageNums = this._getPageNums(totalRecord, perPageRecord);

			this._drawLinks();

		},
		/**
		 * 动态显示
		 */
		_drawLinks : function() {
			var self = this, options = this.options, $el = this.element,currentPage = options.currentPage -1;
			// 去掉重复create方法
			$el.empty();
			// 获取中间显示的页码（开始和结束）
			var interval = self._getInterval();
			// 总页数
			var np = self._getPageNums();
			//··获取页码的点击事件
			var getClickHandler = function(page_id) {
				return function(evt) {
					return self._pageSelected(page_id +1, evt);
				}
			}
			var $ul = $('<ul></ul>');
			// 上一页
			if (options.showPrevNextBtn && currentPage > -1) {
				self._appendItem(currentPage - 1, {
					classes : "pagination-prev ue-state-disable"
				},$ul);
			}
			
			// ...
			if (interval[0] > 0 && options.displayStyle[2] > 0) {
				var end = Math.min(options.displayStyle[2], interval[0]);
				for (var i = 0; i < end; i++) {
					self._appendItem(i,"",$ul);
				}
				if (options.displayStyle[2] < interval[0]) {
					$ul.append('<li class="pagination-ellipsis">...</li>');
				}
			}
			// 中间的页码
			for (var i = interval[0]; i < interval[1]; i++) {
				self._appendItem(i,"",$ul);
			}
			// ...
			if (interval[1] < np && options.displayStyle[2] > 0) {
				if (np - options.displayStyle[2] > interval[1]) {  // 点点点字符串
					// 将点点点添加到容器
					$ul.append('<li class="pagination-ellipsis">...</li>');
				}
				// 末端页码的样式和事件绑定
				var begin = Math.max(np - options.displayStyle[2], interval[1]);
				for (var i = begin; i < np; i++) {
					self._appendItem(i,"",$ul);
				}
			}
			
			// 下一页
			if (currentPage < np && options.showPrevNextBtn) {
				self._appendItem(currentPage + 1, {
					classes : "pagination-next"
				},$ul);
			}
			
			
			
			// 边界样式检测（页号为0时，左暗；页号为最大时，右亮；其余，全亮）
			if(currentPage == 0){
				$el.find('ul').find('.pagination-prev').addClass('ue-state-disable');
				$el.find('ul').find('.pagination-next').removeClass('ue-state-disable');
			}
			else if(currentPage  == self._getPageNums() -1){
				$el.find('ul').find('.pagination-prev').removeClass('ue-state-disable');
				$el.find('ul').find('.pagination-next').addClass('ue-state-disable');
			}
			else{
				 $el.find('ul').find('.pagination-next').removeClass('ue-state-disable');
				 $el.find('ul').find('.pagination-prev').removeClass('ue-state-disable');
			}

			// 记录显示
			if (options.showMessage) {
				var per = options.perPageRecord, nums = options.totalRecord;
				// 无数据时显示的文字及样式
				if(!options.totalRecord){
				}
				else{
					$el.prepend('<div class="uew-pagination-msg">本页显示第&nbsp;' + ((currentPage * per) + 1) + '-' + (((currentPage + 1) * per) > nums ? nums : ((currentPage + 1) * per)) + '&nbsp;条记录，总共&nbsp;' + nums + '&nbsp;条</div>');
				}
			}
			
			// 设置跳到第几页
			/*	if (options.isShowSkip) {
				$el.append("<div><input type='text' name='setPage'/><button class='setPage'>GO</button></div>");
			}
			var $html = "<input type='hidden' value='" + $el.find(					"a.ue-state-current:not(.ue-state-prev,.ue-state-next)").text() 
				+ "' name='pageNo' />" 
				+ "<input type='hidden' value='" 
				+ options.perPageRecord + "' name='pageSize'/>" 
				+ "<input type='hidden' value='" + options.totalRecord + "' name='total'/>";
			$el.append($html);*/
			if( np == 1 ){
				$el.find('ul').find('.pagination-prev').addClass('ue-state-disable');
				$el.find('ul').find('.pagination-next').addClass('ue-state-disable');
			}
			// 去掉边界点击事件
			$el.find('.ue-state-current').children('a').unbind("click");
			$el.find('.ue-state-disable').children('a').unbind("click");
		},
		
		/**
		 *拼接样式
		 */
		_appendItem : function(id, appendopts,$ul) {
			var self = this, options = this.options, $el = this.element;
			var currentPage = options.currentPage -1;
			var $li = $('<li class="ue-state-default"></li>');
			id = id < 0 ? 0 : (id < this.pageNums ? id : this.pageNums - 1);
			appendopts = $.extend({
				text : id + 1,
				classes : ""
			}, appendopts || {});
			var lnk;
			if (id == currentPage) {
				lnk = $("<a>" + appendopts.text + "</a>").bind("click", self._getClickHandler(id));
				if(appendopts.classes.split(" ")[0] == 'pagination-prev' || appendopts.classes.split(" ")[0] == 'pagination-next'){}
				else{
					$li.addClass('ue-state-current');
				}
			} else {
				lnk = $("<a>" + (appendopts.text) + "</a>").bind("click", self._getClickHandler(id));	
			}
			if (appendopts.classes) {
				$li.addClass(appendopts.classes);
				if(appendopts.classes.split(" ")[0] === 'pagination-prev'){
					lnk.text("").append('<span class="uew-icon uew-icon-carat-1-w"></span>');
				}
				else if(appendopts.classes.split(" ")[0] === 'pagination-next'){
					lnk.text("").append('<span class="uew-icon uew-icon-carat-1-e"></span>');
				}
			}
			$li.append(lnk);
			$ul.append($li);
			$el.append($ul);
			
		},
		/**
		 *闭包，返回当前页id
		 */
		_getClickHandler : function(id) {
			var self = this, options = this.options, $el = this.element;
			return function(evt) {
				return self._pageSelected(id+1, evt);
			}
		},
		
		/**
		 * 得到总页数
		 */
		_getPageNums : function() {
			var options = this.options;
			// 数据总数
			var totalRecord = options.totalRecord;
			// 每页个数
			var perPageRecord = options.perPageRecord;
			return Math.ceil(totalRecord / perPageRecord);
		},
		/**
		 * 得到中间显示的间隔
		 * 参数num：显示类型数组，pageNums：总页数
		 */
		_getInterval : function() {
			var $el = this.element, options = this.options, self = this, currentPage = options.currentPage -1;
			//··中间和末尾显示页面的个数
			var ne_half = parseInt(options.displayStyle[1]) + parseInt(options.displayStyle[2]);
			//··总页数
			var pages = this._getPageNums();
			//··总页数 - 中间显示页数
			var upper_limit = pages - options.displayStyle[1];
			
			//··计算中间显示页码的开始
			var start = currentPage+1 >= ne_half ? Math.max(Math.min(parseInt(currentPage)+2
									- options.displayStyle[1], upper_limit), 0) : 0;
			//··计算中间显示页码的结束
			var end = currentPage+1 >= ne_half ? Math.min(parseInt(currentPage) + 2,
					pages) : Math.min(ne_half, pages);
			
			return [start, end];
		},
		/**
		 * 选中页数的单击事件
		 */
		_pageSelected : function(id, evt) {
			var $el = this.element, options = this.options, self = this;
			options.currentPage  = id;
			_callback = options.callBack || options.callback;
			var callBack = _callback(id, $el);
			this._drawLinks();
			if (!callBack) {
				if (evt.stopPropagation) {
					evt.stopPropagation();
				} else {
					evt.cancelBubble = true;
				}
			}
			return callBack;
		}
		
	});
})(jQuery);
