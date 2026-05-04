  <template>
	<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
		<view class="content">
			<view :style='{"minHeight":"100vh","alignContent":"flex-start","padding":"20rpx 0 240rpx 0","alignItems":"flex-start","flexWrap":"wrap","background":"#ffffff","display":"block","width":"100%","position":"relative","height":"auto"}'>
				<view class="cu-bar bg-white search" :style='{"width":"100%","padding":"24rpx 24rpx 20rpx 24rpx","background":"#ffffff","display":"flex","height":"auto","order":"3"}'>
					<view  :style='{"margin":"0 12rpx","flex":"1","position":"relative"}' class="search-form round">
						<text class="icon iconfont icon-fangdajing07" :style='{"color":" #999999","left":"0px","textAlign":"center","width":"80rpx","fontSize":"40rpx","lineHeight":"80rpx","position":"absolute"}'></text>
						<input placeholder-style="color: #999" :style='{"border":"2rpx solid #dddddd","padding":"12rpx 20rpx 12rpx 80rpx","color":"#333333","borderRadius":"60rpx","background":"#fff","width":"100%","lineHeight":"56rpx","fontSize":"28rpx","height":"80rpx"}' v-model="searchForm.xiangmumingcheng" type="text" placeholder="项目名称" ></input>
					</view>
					<button :style='{"border":"0","padding":"0px","margin":"0","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"136rpx","lineHeight":"80rpx","fontSize":"28rpx","height":"80rpx"}' @tap="search" class="cu-btn shadow-blur round">搜索</button>
				</view>
			

				<view :style='{"width":"80%","margin":"20rpx 10% 20rpx 10%","flexWrap":"wrap","justifyContent":"center","display":"flex","order":"3"}'>
					<view @click="sortClick('addtime')" :style='{"border":"0","padding":"0 12rpx 0 12rpx","margin":"0 8rpx 0 0","outline":"0","borderRadius":"8rpx","background":"none","display":"flex"}'>
						<text :style='{"color":"#333333","lineHeight":"48rpx","fontSize":"24rpx"}'>按日期</text>
						<text v-if="listSort!='addtime'" class="icon iconfont icon-shijian21" :style='{"margin":"0 0 0 4rpx","lineHeight":"48rpx","fontSize":"24rpx","color":"#333333"}'></text>
						<text v-else-if="listSort=='addtime'&&listOrder=='asc'" class="icon iconfont icon-shijian21" :style='{"margin":"0 0 0 4rpx","lineHeight":"48rpx","fontSize":"24rpx","color":"#333333"}'></text>
						<text v-else-if="listSort=='addtime'&&listOrder=='desc'" class="icon iconfont icon-shijian21" :style='{"margin":"0 0 0 4rpx","lineHeight":"48rpx","fontSize":"24rpx","color":"#333333"}'></text>
					</view>
					<view @click="sortClick('totalscore')" :style='{"border":"0","padding":"0 12rpx 0 12rpx","margin":"0 8rpx 0 0","outline":"0","borderRadius":"8rpx","background":"none","display":"flex"}'>
						<text :style='{"color":"#333333","lineHeight":"48rpx","fontSize":"24rpx"}'>评分</text>
						<text v-if="listSort!='totalscore'" class="icon iconfont icon-shijian18" :style='{"margin":"0 0 0 4rpx","lineHeight":"48rpx","fontSize":"24rpx","color":"#333333"}'></text>
						<text v-else-if="listSort=='totalscore'&&listOrder=='asc'" class="icon iconfont icon-shijian18" :style='{"margin":"0 0 0 4rpx","lineHeight":"48rpx","fontSize":"24rpx","color":"#333333"}'></text>
						<text v-else-if="listSort=='totalscore'&&listOrder=='desc'" class="icon iconfont icon-shijian18" :style='{"margin":"0 0 0 4rpx","lineHeight":"48rpx","fontSize":"24rpx","color":"#333333"}'></text>
					</view>
				</view>
				<view :style='{"alignContent":"flex-start","alignItems":"flex-start","flexWrap":"wrap","background":"#ffffff","display":"flex","width":"100%","position":"initial","height":"auto","order":"4"}'>
					<scroll-view scroll-x="true" class="category-one" :style='{"padding":"20rpx","margin":"0px 0 20rpx 0","borderColor":"#dddddd","whiteSpace":"nowrap","background":"#ff6f9f","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
						<view :class='categoryName === item.fuwuleixing ? "active" : ""' class="tab" v-for="(item,index) in categoryList" :key="index" @tap="categoryClick(index)">{{item.displayName}}</view>
					</scroll-view>
					<!-- 样式2 -->
					<view class="list" :style='{"padding":"24rpx","margin":"0","flexWrap":"wrap","display":"flex","width":"100%","justifyContent":"space-between","height":"auto","order":"4"}'>
						<view @tap="onDetailTap(product)" class="listm flex flex-between" :style='{"padding":"0 0 20rpx 0","boxShadow":"0 2rpx 12rpx #cccccc","margin":"0 0 30rpx 0","borderColor":"#eeeeee","borderRadius":"12rpx","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0 0 0px 0","display":"flex","width":"48%","borderStyle":"solid","height":"auto"}' v-for="(product,index) in list" :key="product.id || index">
							<image lazy-load :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx 12rpx 0 0","display":"block","height":"320rpx"}' mode="aspectFill" class="listmpic" v-if="preHttp(product.xiangmutupian)" :src="product.xiangmutupian.split(',')[0]"></image>
							<image lazy-load :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx 12rpx 0 0","display":"block","height":"320rpx"}' mode="aspectFill" class="listmpic" v-else :src="product.xiangmutupian?baseUrl+product.xiangmutupian.split(',')[0]:getDefaultImage(product.id)"></image>

							<view class="listmr" :style='{"width":"100%","padding":"0 20rpx 0 20rpx","margin":"0","flexWrap":"wrap","display":"flex","height":"auto"}'>
								<view class="col3 f30 elip mb15" :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"1.8","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"500"}'>{{product.xiangmubianhao}}</view>
								<view class="col3 f30 elip mb15" :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"1.8","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"500"}'>项目名称:{{product.xiangmumingcheng}}</view>
								<view class="col3 f30 elip mb15" :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"1.8","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"500"}'>{{product.fuwuleixing}}</view>
								<view :style='{"padding":"0 10rpx 0 0","order":"5"}'>
									<text class="icon iconfont icon-shijian21" :style='{"margin":"0 0px 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#999999"}'></text>
									<text :style='{"color":"#999999","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.addtime.split(' ')[0].replace(/\-/g,'-')}}</text>
								</view>
								<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"1"}'>
									<text class="icon iconfont icon-zan10" :style='{"margin":"0 0px 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#999999"}'></text>
									<text :style='{"color":"#999999","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.thumbsupnum}}</text>
								</view>
								<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"2"}'>
									<text class="icon iconfont icon-shoucang10" :style='{"margin":"0 0px 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#999999"}'></text>
									<text :style='{"color":"#999999","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.storeupnum}}</text>
								</view>
							</view>
							<!-- #ifdef MP-WEIXIN -->
							<view :style='{"width":"100%","padding":"8rpx 20rpx 8rpx 20rpx","justifyContent":"space-between","display":"flex","height":"auto"}'>
								<view :style='{"display":"flex"}' v-if="(userid && isAuth('fuwuxinxi','修改')) || (!userid && isAuthFront('fuwuxinxi','修改'))" @tap.stop.proevent="onUpdate" :data-row="product">
									<text :style='{"margin":"0 8rpx 0 0","fontSize":"28rpx","lineHeight":"1","color":"#ff6f9f","display":"inline-block"}' class="cuIcon-edit"></text>
									<text :style='{"fontSize":"28rpx","lineHeight":"1","color":"#ff6f9f","display":"inline-block"}'>修改</text>
								</view>
								<view :style='{"display":"flex"}' v-if="(userid && isAuth('fuwuxinxi','删除')) || (!userid && isAuthFront('fuwuxinxi','删除'))" @tap.stop.proevent="onDelete" :data-row="product">
									<text :style='{"margin":"0 8rpx 0 0","fontSize":"28rpx","lineHeight":"1","color":"#ff0000","display":"inline-block"}' class="cuIcon-delete"></text>
									<text :style='{"fontSize":"28rpx","lineHeight":"1","color":"#ff0000","display":"inline-block"}'>删除</text>
								</view>
							</view>
							<!-- #endif -->
							<!-- #ifndef MP-WEIXIN -->
							<view :style='{"width":"100%","padding":"8rpx 20rpx 8rpx 20rpx","justifyContent":"space-between","display":"flex","height":"auto"}'>
								<view :style='{"display":"flex"}' v-if="(userid && isAuth('fuwuxinxi','修改')) || (!userid && isAuthFront('fuwuxinxi','修改'))" @tap.stop.proevent="onUpdateTap(product)">
									<text :style='{"margin":"0 8rpx 0 0","fontSize":"28rpx","lineHeight":"1","color":"#ff6f9f","display":"inline-block"}' class="cuIcon-edit"></text>
									<text :style='{"fontSize":"28rpx","lineHeight":"1","color":"#ff6f9f","display":"inline-block"}'>修改</text>
								</view>
								<view :style='{"display":"flex"}' v-if="(userid && isAuth('fuwuxinxi','删除')) || (!userid && isAuthFront('fuwuxinxi','删除'))" @tap.stop.proevent="onDeleteTap(product.id)">
									<text :style='{"margin":"0 8rpx 0 0","fontSize":"28rpx","lineHeight":"1","color":"#ff0000","display":"inline-block"}' class="cuIcon-delete"></text>
									<text :style='{"fontSize":"28rpx","lineHeight":"1","color":"#ff0000","display":"inline-block"}'>删除</text>
								</view>
							</view>
							<!-- #endif -->
						</view>
					</view>
			
			
			


			
			
			
			
				</view>
				<button :style='{"border":"0","boxShadow":"0 2rpx 12rpx #00000030","whiteSpace":"nowrap","color":"#fff","alignItems":"center","display":"flex","justifyContent":"center","outline":"none","borderRadius":"100%","top":"136rpx","left":"8rpx","background":"#ff6f9f","width":"84rpx","lineHeight":"84rpx","fontSize":"28rpx","position":"absolute","height":"84rpx","zIndex":"999"}' class="add-btn" @click="screenBoxShow=true">筛</button>
				<button :style='{"border":"0","boxShadow":"0 2rpx 12rpx #999999","whiteSpace":"nowrap","color":"#fff","alignItems":"center","bottom":"128rpx","display":"flex","right":"128rpx","justifyContent":"center","outline":"none","borderRadius":"100%","background":"#ff6f9f","width":"84rpx","lineHeight":"84rpx","fontSize":"28rpx","position":"absolute","height":"84rpx","zIndex":"999"}' v-if="userid && isAuth('fuwuxinxi','新增')" class="add-btn" @click="onAddTap()">新增</button>
				<button :style='{"border":"0","boxShadow":"0 2rpx 12rpx #999999","whiteSpace":"nowrap","color":"#fff","alignItems":"center","bottom":"128rpx","display":"flex","right":"128rpx","justifyContent":"center","outline":"none","borderRadius":"100%","background":"#ff6f9f","width":"84rpx","lineHeight":"84rpx","fontSize":"28rpx","position":"absolute","height":"84rpx","zIndex":"999"}' v-if="!userid && isAuthFront('fuwuxinxi','新增')" class="add-btn" @click="onAddTap()">新增</button>
				<button :style='{"border":"0","boxShadow":"0 2rpx 12rpx #999999","whiteSpace":"nowrap","color":"#fff","alignItems":"center","bottom":"128rpx","display":"flex","justifyContent":"center","outline":"none","borderRadius":"100%","left":"8rpx","background":"#ffaa33","width":"84rpx","lineHeight":"84rpx","fontSize":"28rpx","position":"absolute","height":"84rpx","zIndex":"999"}' v-if="staticType" class="statics-btn" @click="staticClick()">
					统
				</button>
				<view :style='{"top":"0","left":"0","background":"#00000030","width":"100%","position":"absolute","height":"100%","zIndex":"665"}' v-if="screenBoxShow" @click="screenBoxShow=false"></view>
				<view class="screenBox" :class="screenBoxShow?'screenBoxActive':''">
					<view  :style='{"width":"100%","padding":"20rpx 0 20rpx 0"}'>
						<view :style='{"width":"100%","padding":"0 0 20rpx 20rpx","fontSize":"24rpx"}'>服务类型</view>
						<view :style='{"width":"100%","flexWrap":"wrap","justifyContent":"space-around","display":"flex"}'>
							<view @click="fuwuleixingChange(item)" v-for="(item,index) in fuwuleixingOptions" :key="index" class="screenTab" :class="searchForm.fuwuleixing==item?'screenTabActive':''">{{item}}</view>
						</view>
					</view>
					<view  :style='{"width":"100%","padding":"20rpx 0 20rpx 0","alignItems":"center","justifyContent":"space-around","display":"flex"}'>
						<view :style='{"width":"auto","padding":"0","fontSize":"24rpx","whiteSpace":"nowrap"}'>营业时间</view>
						<input :style='{"border":"0","width":"80%","padding":"0 20rpx 0 20rpx","borderRadius":"40rpx","background":"#fff","height":"60rpx"}' placeholder="请输入营业时间" v-model="searchForm.yingyeshijian">
					</view>
					<view :style='{"width":"100%","padding":"40rpx 0 0","alignItems":"center","justifyContent":"space-around","display":"flex"}'>
						<div :style='{"width":"30%","lineHeight":"60rpx","color":"#888","textAlign":"center","background":"#fff","height":"60rpx"}' @click="screenReset">重置</div>
						<div :style='{"width":"30%","lineHeight":"60rpx","color":"#fff","textAlign":"center","background":"#138bff","height":"60rpx"}' @click="search">搜索</div>
					</view>
				</view>
			</view>
		</view>
		<!-- <view v-if="scrollTop>200" @tap="scrollTopClick" :style='{"boxShadow":"0 8rpx 16rpx rgba(0,0,0,.3)","borderRadius":"50%","textAlign":"center","bottom":"20%","background":"#ff000030","width":"60rpx","lineHeight":"60rpx","position":"fixed","right":"20rpx","height":"60rpx","zIndex":"999"}'>
			<span class="icon iconfont icon-jiantou07" :style='{"color":"#fff"}'></span>
		</view> -->
	</mescroll-uni>
</template>

<script>
	export default {
		data() {
			return {
				fuwuleixingOptions:[],
				btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
				list: [],
				lists: [],
                userid: '',
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					page: {
						size: 8
					},
					noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
					onScroll: true,
					toTop: true
				},
				hasNext: true,
				searchForm:{
					xiangmumingcheng: '',
					fuwuleixing: '',
					yingyeshijian: '',
				},
				categoryList:[],
				categoryName :'全部',
				CustomBar: '0',
				listSort: 'id',
				listOrder: 'desc',
				screenBoxShow: false,
				scrollTop: 0,
				staticType: false,
				chartData1: {},
				opts1: {},
				chartData2: {},
				opts2: {},
				chartData3: {},
				opts3: {},
				chartData4: {},
				opts4: {},
			};
		},
		watch: {
		},
		mounted() {
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			username() {
				return uni.getStorageSync("nickname")
			},
			role() {
				return uni.getStorageSync("appRole")
			},
		},
		onUnload() {
			uni.removeStorageSync("useridTag");
		},
		async onShow() {
			if(uni.getStorageSync("useridTag")==1){
				this.userid=uni.getStorageSync("useridTag");
				if(
					this.isAuth('fuwuxinxi','服务类型占比')
					||this.isAuth('fuwuxinxi','服务项目收藏量')
					||this.isAuth('fuwuxinxi','服务项目评分值统计')
					||this.isAuth('fuwuxinxi','服务项目点赞量统计')
				) {
					this.staticType = true
				}else {
					this.staticType = false
				}
				let remindRes = null
			} else {
				this.userid = "";
				if(
					this.isAuthFront('fuwuxinxi','服务类型占比')
					||this.isAuthFront('fuwuxinxi','服务项目收藏量')
					||this.isAuthFront('fuwuxinxi','服务项目评分值统计')
					||this.isAuthFront('fuwuxinxi','服务项目点赞量统计')
				) {
					this.staticType = true
				}else {
					this.staticType = false
				}
			}
			this.btnColor = this.btnColor.sort(()=> {
				return (0.5-Math.random());
			});
			await this.loadCategoryList()
			this.hasNext = true
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
			this.$forceUpdate()
		},
		async onLoad(options) {
			this.hasNext = true
			// 重新加载数据
			// if (this.mescroll) this.mescroll.resetUpScroll()
			let res = {};
			//fuwuleixing筛选
			res = await this.$api.option(`fuwuleixing`,`fuwuleixing`,{});
			this.fuwuleixingOptions = res.data;
		},
		components: {
		},
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			scrollChange(e){
				this.scrollTop = e.scrollTop
			},
			scrollTopClick(){
				uni.pageScrollTo({
					scrollTop: 0
				})
			},
			fuwuleixingChange(e){
				this.searchForm.fuwuleixing = e
				this.$forceUpdate()
			},
			screenReset(){
				this.searchForm = {}
				this.search()
				this.$forceUpdate()
			},
			uGetRect(selector, all) {
				return new Promise(resolve => {
					uni.createSelectorQuery()
					.in(this)
					[all ? 'selectAll' : 'select'](selector)
					.boundingClientRect(rect => {
						if (all && Array.isArray(rect) && rect.length) {
							resolve(rect);
						}
						if (!all && rect) {
							resolve(rect);
						}
					})
					.exec();
				});
			},
			cloneData(data) {
				return JSON.parse(JSON.stringify(data));
			},
			sortClick(type){
				if(this.listSort==type){
					if(this.listOrder == 'desc'){
						this.listOrder = 'asc'
					}else{
						this.listOrder = 'desc'
					}
				}else{
					this.listSort = type
					this.listOrder = 'desc'
				}
				this.search()
			},
            priceChange(price) {
                return Number(price).toFixed(2);
            },
			preHttp(str) {
			    return str && str.substr(0,4)=='http';
			},
			getDefaultImage(id){
				const random = (id * 9973) % 22 + 1
				return `/static/default/default${random}.jpg`
			},
			async loadCategoryList() {
				let res = this.userid ? await this.$api.page('fuwuleixing', {page:1,limit:100}) : await this.$api.list('fuwuleixing', {page:1,limit:100})
				let countRes = await this.$api.typeCounts('fuwuxinxi', this.userid ? {} : {onshelves: 1})
				let counts = (countRes && countRes.data) || {}
				let total = Number((countRes && countRes.total) || 0)
				let list = (res.data && res.data.list ? res.data.list : []).map(item => {
					let count = Number(counts[item.fuwuleixing] || 0)
					return Object.assign({}, item, {count: count, displayName: `${item.fuwuleixing}(${count})`})
				})
				list.splice(0,0,{id:0,fuwuleixing:'全部',count:total,displayName:`全部(${total})`})
				this.categoryList = list
			},
			//类别搜索
			categoryClick(index){
				let item = this.categoryList[index]
				if(!item) return
				let categoryName = item.fuwuleixing
				this.categoryName = categoryName;
				this.mescroll.resetUpScroll();
			},
			// mescroll组件初始化的回调,可获取到mescroll对象
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},
			/*下拉刷新的回调 */
			downCallback(mescroll) {
				this.hasNext = true
				// 重置分页参数页数为1
				mescroll.resetUpScroll()
			},
			/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
			async upCallback(mescroll) {
				let params = {
					page: mescroll.num,
					limit: mescroll.size,
				}
				if(!this.userid) {
					params.onshelves = 1
				}
				params['sort'] = this.listSort;
				params['order'] = this.listOrder;

				if(this.categoryName!='全部'){
					params.fuwuleixing = '%' + this.categoryName + '%'
				}
				if(this.searchForm.xiangmumingcheng){
					params['xiangmumingcheng'] = '%' + this.searchForm.xiangmumingcheng + '%'
				}
				if(this.searchForm.fuwuleixing){
					params['fuwuleixing'] = '%' + this.searchForm.fuwuleixing + '%'
				}
				if(this.searchForm.yingyeshijian){
					params['yingyeshijian'] = '%' + this.searchForm.yingyeshijian + '%'
				}
				let user = uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
                let res = {}
                if(this.userid) {
                    res = await this.$api.page(`fuwuxinxi`, params);
                } else {
                    res = await this.$api.list(`fuwuxinxi`, params);
                }

				// 如果是第一页数据置空
				if (mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				let total = Number(res.data.total || 0)
				this.hasNext = total ? this.list.length < total : res.data.list.length >= mescroll.size;
				mescroll.endSuccess(res.data.list.length, this.hasNext);
			},
			// 详情
			onDetailTap(item) {
                uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
			},
			onUpdate(e){
				this.onUpdateTap(e.currentTarget.dataset.row)
			},
			// 修改
			onUpdateTap(row){
				uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./add-or-update?id=${row.id}`)
			},
			// 添加
			onAddTap(){
                uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./add-or-update`)
			},
			onDelete(e){
				this.onDeleteTap(e.currentTarget.dataset.row.id)
			},
			onDeleteTap(id){
				var that = this;
				uni.showModal({
					title: '提示',
					content: '是否确认删除',
					success: async function(res) {
						if (res.confirm) {
							await that.$api.del('fuwuxinxi', JSON.stringify([id]));
							let obj = await that.$api.list('storeup',{
								page: 1,
								limit: 100,
								tablename: 'fuwuxinxi',
								refid: id,
							})
							if(obj.data.list.length){
								let arr = []
								for(let x in obj.data.list){
									arr.push(obj.data.list[x].id)
								}
								await that.$api.del('storeup',JSON.stringify(arr))
							}
							that.$utils.msg('删除成功');
							that.hasNext = true
							// 重置分页参数页数为1
							that.search()
						}
					}
				});
			},
			// 搜索
			async search(){
				this.mescroll.num = 1
				let searchForm = {
					page: this.mescroll.num,
					limit: this.mescroll.size,
				}
				if(!this.userid) {
					searchForm.onshelves = 1
				}
				searchForm['sort'] = this.listSort;
				searchForm['order'] = this.listOrder;

				if(this.searchForm.xiangmumingcheng){
					searchForm['xiangmumingcheng'] = '%' + this.searchForm.xiangmumingcheng + '%'
				}
				if(this.categoryName!='全部'){
					searchForm.fuwuleixing = '%' + this.categoryName + '%'
				}
				if(this.searchForm.fuwuleixing){
					searchForm['fuwuleixing'] = '%' + this.searchForm.fuwuleixing + '%'
				}
				if(this.searchForm.yingyeshijian){
					searchForm['yingyeshijian'] = '%' + this.searchForm.yingyeshijian + '%'
				}
				let user = uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
				let res = {};
				if(this.userid) {
					res = await this.$api.page(`fuwuxinxi`, searchForm);
				} else {
					res = await this.$api.list(`fuwuxinxi`, searchForm);
				}
				// 如果是第一页数据置空
				if (this.mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				
				let total = Number(res.data.total || 0)
				this.hasNext = total ? this.list.length < total : res.data.list.length >= this.mescroll.size;
				this.mescroll.endSuccess(res.data.list.length, this.hasNext);
				this.screenBoxShow = false
			},
			staticClick() {
				this.$utils.jump(`./add-or-update?showType=2&userid=` + this.userid)
			},
		}
	};
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	.category-one .tab {
		cursor: pointer;
		padding: 0;
		margin: 0 40rpx 0 0;
		color: #fff;
		background: none;
		display: inline-block;
		width: auto;
		font-size: 28rpx;
		line-height: 80rpx;
		text-align: center;
	}
	
	.category-one .tab.active {
		cursor: pointer;
		padding: 0;
		margin: 0 40rpx 0 0;
		color: #ffffff;
		display: inline-block;
		font-size: 28rpx;
		border-color: #ffffff;
		line-height: 80rpx;
		border-radius: 0px;
		background: none;
		width: auto;
		border-width:  0 0 4rpx 0;
		border-style: solid;
		text-align: center;
	}
	.screenBox {
		padding: 40rpx 0 20rpx 0;
		transform: translate3d(100%, 0, 0);
		z-index: 666;
		top: 0;
		background: #a7cff9;
		width: 80%;
		position: absolute;
		right: 0;
		transition: transform .3s;
		height: 100%;
		.screenTab {
			border-radius: 40rpx;
			margin: 10rpx 0 10rpx 0;
			background: #fff;
			width: 31%;
			line-height: 72rpx;
			text-align: center;
		}
		.screenTabActive {
			border-radius: 40rpx;
			margin: 10rpx 0 10rpx 0;
			background: #138bff;
			width: 31%;
			line-height: 72rpx;
			text-align: center;
		}
	}
	.screenBoxActive {
		transform: translate3d(0, 0, 0);
	}
</style>
