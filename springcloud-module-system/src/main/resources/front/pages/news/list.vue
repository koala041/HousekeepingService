  <template>
	<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
		<view class="content">
			<view :style='{"minHeight":"100vh","alignContent":"flex-start","padding":"20rpx 0 240rpx 0","alignItems":"flex-start","flexWrap":"wrap","background":"#ffffff","display":"block","width":"100%","position":"relative","height":"auto"}'>
				<view class="cu-bar bg-white search" :style='{"width":"100%","padding":"24rpx 24rpx 20rpx 24rpx","background":"#ffffff","display":"flex","height":"auto","order":"3"}'>
					<view  :style='{"margin":"0 12rpx","flex":"1","position":"relative"}' class="search-form round">
						<text class="icon iconfont icon-fangdajing07" :style='{"color":" #999999","left":"0px","textAlign":"center","width":"80rpx","fontSize":"40rpx","lineHeight":"80rpx","position":"absolute"}'></text>
						<input placeholder-style="color: #999" :style='{"border":"2rpx solid #dddddd","padding":"12rpx 20rpx 12rpx 80rpx","color":"#333333","borderRadius":"60rpx","background":"#fff","width":"100%","lineHeight":"56rpx","fontSize":"28rpx","height":"80rpx"}' v-model="searchForm.title" type="text" placeholder="标题" ></input>
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
				</view>
				<view :style='{"alignContent":"flex-start","alignItems":"flex-start","flexWrap":"wrap","background":"#ffffff","display":"flex","width":"100%","position":"initial","height":"auto","order":"4"}'>
					<!-- 样式1 -->
					<view class="news-box1" :style='{"width":"100%","padding":"24rpx","margin":"0","height":"auto","order":"5"}'>
						<view @tap="onDetailTap(product)" v-for="(product,index) in list" :key="index" class="list-item" :style='{"padding":"20rpx","boxShadow":"0 2rpx 12rpx #cccccc","margin":"0 0 30rpx 0","borderColor":"#dddddd","borderRadius":"12rpx","background":"#fff","borderWidth":"0 0 0px 0","width":"100%","borderStyle":"solid","height":"auto"}'>
							<view class="list-item-body" :style='{"width":"auto","padding":"0","margin":"0","flex":"1","height":"auto"}'>
								<view :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#000","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"500"}' class="title ">{{product.title}}</view>
								<view :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#666","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis"}' class="text ">{{product.introduction}}</view>
								<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"5"}'>
									<text class="icon iconfont icon-shijian21" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#999"}'></text>
									<text :style='{"color":"#999","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.addtime.split(' ')[0].replace(/\-/g,'-')}}</text>
								</view>
								<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"4"}'>
									<text class="icon iconfont icon-geren16" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#999"}'></text>
									<text :style='{"color":"#999","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.name}}</text>
								</view>
							</view>
							<image :style='{"width":"240rpx","objectFit":"cover","borderRadius":"12rpx","display":"none","height":"240rpx"}' mode="aspectFill" class="listmpic" :src="baseUrl+product.picture"></image>
						</view>
					</view>
			
			
			
			
			


			
			
			
			
				</view>
				<button :style='{"border":"0","boxShadow":"0 2rpx 12rpx #999999","whiteSpace":"nowrap","color":"#fff","alignItems":"center","bottom":"128rpx","display":"flex","right":"128rpx","justifyContent":"center","outline":"none","borderRadius":"100%","background":"#ff6f9f","width":"84rpx","lineHeight":"84rpx","fontSize":"28rpx","position":"absolute","height":"84rpx","zIndex":"999"}' v-if="userid && isAuth('news','新增')" class="add-btn" @click="onAddTap()">新增</button>
				<button :style='{"border":"0","boxShadow":"0 2rpx 12rpx #999999","whiteSpace":"nowrap","color":"#fff","alignItems":"center","bottom":"128rpx","display":"flex","right":"128rpx","justifyContent":"center","outline":"none","borderRadius":"100%","background":"#ff6f9f","width":"84rpx","lineHeight":"84rpx","fontSize":"28rpx","position":"absolute","height":"84rpx","zIndex":"999"}' v-if="!userid && isAuthFront('news','新增')" class="add-btn" @click="onAddTap()">新增</button>
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
				btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
				list: [],
				lists: [],
                userid: '',
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
					onScroll: true,
					toTop: true
				},
				hasNext: true,
				searchForm:{
					title: '',
				},
				CustomBar: '0',
				listSort: 'id',
				listOrder: 'desc',
				screenBoxShow: false,
				scrollTop: 0,
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
				let remindRes = null
			} else {
				this.userid = "";
			}
			this.btnColor = this.btnColor.sort(()=> {
				return (0.5-Math.random());
			});
			this.hasNext = true
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
			this.$forceUpdate()
		},
		async onLoad(options) {
			this.hasNext = true
			// 重新加载数据
			// if (this.mescroll) this.mescroll.resetUpScroll()
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
			//类别搜索
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
				params['sort'] = this.listSort;
				params['order'] = this.listOrder;

				if(this.searchForm.title){
					params['title'] = '%' + this.searchForm.title + '%'
				}
				let user = uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
                let res = {}
                if(this.userid) {
                    res = await this.$api.page(`news`, params);
                } else {
                    res = await this.$api.list(`news`, params);
                }

				// 如果是第一页数据置空
				if (mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				this.$forceUpdate()
				
				let length = Math.ceil(this.list.length/6)
				let arr = [];
				for (let i = 0; i<length; i++){
					arr[i] = this.list.slice(i*6, (i+1)*6)
				}
				this.lists = arr
				if (res.data.list.length == 0) this.hasNext = false;
				mescroll.endSuccess(mescroll.size, this.hasNext);
			},
			// 详情
			onDetailTap(item) {
                uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`../news-detail/news-detail?id=${item.id}`)
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
							await that.$api.del('news', JSON.stringify([id]));
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
				searchForm['sort'] = this.listSort;
				searchForm['order'] = this.listOrder;

				if(this.searchForm.title){
					searchForm['title'] = '%' + this.searchForm.title + '%'
				}
				let user = uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
				let res = {};
				if(this.userid) {
					res = await this.$api.page(`news`, searchForm);
				} else {
					res = await this.$api.list(`news`, searchForm);
				}
				// 如果是第一页数据置空
				if (this.mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				
				let length = Math.ceil(this.list.length/6)
				let arr = [];
				for (let i = 0; i<length; i++){
					arr[i] = this.list.slice(i*6, (i+1)*6)
				}
				this.lists = arr
				if (res.data.list.length == 0) this.hasNext = false;
				this.mescroll.endSuccess(this.mescroll.size, this.hasNext);
				this.screenBoxShow = false
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
</style>
