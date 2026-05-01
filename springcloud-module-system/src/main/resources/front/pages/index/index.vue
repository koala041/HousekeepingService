<template>
	<view class="content">
		<view :style='{"minHeight":"100%","padding":"24rpx","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","height":"auto"}'>
			<view class="list-swiper-4" :style='{"width":"100%","padding":"0","position":"relative","background":"none","height":"480rpx"}' @touchstart="touchStart" @touchmove="touchMove" @touchend="touchEnd">
				<view :style='{"width":"100%","position":"absolute","top":"0","left":"0","background":"#fff","height":"480rpx"}' class="item animate__animated" :class="prevNumList4 == index  ? 'animate__fadeOut' : (numList4 == index  ? 'animate__fadeIn' : '')" v-for="(swiper,index) in swiperList" :key="index" v-if="numList4 == index || prevNumList4 == index">
					<image :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"480rpx"}' mode="aspectFill" :src="baseUrl+swiper.img" @tap="onSwiperTap(swiper)"></image>
					<view :style='{"padding":"8rpx 20rpx","margin":"-20rpx 0 0 0","transform":"translate3d(-50%, -50%, 0)","top":"50%","color":"#000","left":"50%","background":"#ffffff30","display":"none","lineHeight":"1.5","fontSize":"40rpx","position":"absolute"}'>{{ swiper.title }}</view>
				</view>
				<view class="animate__navigation" :style='{"alignItems":"center","left":"0","bottom":"20rpx","background":"none","display":"flex","width":"100%","position":"absolute","justifyContent":"center","height":"40rpx"}'>
					<block v-for="(swiper,index) in swiperList" :key="index">
						<text class="navigation-item" v-if="numList4 == index" :style='{"width":"16rpx","margin":"0 4rpx","borderRadius":"100%","background":"#ff6f9f","height":"16rpx"}'></text>
						<text class="navigation-item" v-if="numList4 != index" :style='{"width":"16rpx","margin":"0 4rpx","borderRadius":"100%","background":"#fff","height":"16rpx"}'></text>
					</block>
				</view>
			</view>
			<!-- menu -->
			<view v-if="true" class="menu_view">
				<block v-for="(item,index1) in menuList" v-bind:key="item.roleName">
					<block v-if="index1==0" v-bind:key="index" v-for=" (menu,index) in item.frontMenu">
						<block v-bind:key="sort" v-for=" (child,sort) in menu.child">
							<block v-bind:key="sort2" v-for=" (button,sort2) in child.buttons">
								<view :style="{'background':menuColor[index]}" class="menu-item" v-if="button=='查看' && child.tableName!='yifahuodingdan' && child.tableName!='yituikuandingdan' &&child.tableName!='yiquxiaodingdan' && child.tableName!='weizhifudingdan' && child.tableName!='yizhifudingdan' && child.tableName!='yiwanchengdingdan' " @tap="onPageTap2(child.tableName)">
									<view class="iconarr" :class="child.appFrontIcon" ></view>
									
									<view class="text">{{child.menu.split("列表")[0]}}</view>
								</view>
							</block>
						</block>
					</block>
				</block>
			</view>
			<!-- 商品推荐 -->
			<view class="listBox recommend" :style='{"width":"100%","margin":"40rpx 0 40rpx 0","background":"#ffffff","order":"2"}'>
				<view class="title" :style='{"width":"100%","padding":"0","margin":"0 0  30rpx 0","background":"none","justifyContent":"space-between","display":"flex"}'>
					<view :style='{"padding":"0 0 0 10rpx","borderColor":"#ff6f9f","color":"#ff6f9f","borderWidth":"0 0 0 4rpx","fontSize":"36rpx","lineHeight":"1","borderStyle":"solid"}'>服务信息推荐</view>
					<view :style='{"alignItems":"center","justifyContent":"center","display":"flex"}' @tap="onPageTap('fuwuxinxi')">
						<text :style='{"color":"#ff6f9f","fontSize":"28rpx","textDecoration":"underline"}'>MORE</text>
						<text class="icon iconfont icon-jinru" :style='{"color":"#666","fontSize":"28rpx","display":"none"}'></text>
					</view>
				</view>
				<!-- 样式3 -->
				<view class="list-box style3" :style='{"padding":"0","margin":"0","flexWrap":"wrap","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
					<view @tap="onDetailTap('fuwuxinxi',product.id)" v-for="(product,index) in fuwuxinxilist" :key="index" class="list-item" :style='{"padding":"0 0 20rpx 0","boxShadow":"0 2rpx 12rpx #cccccc","margin":"0 0 30rpx 0","borderColor":"#dddddd","borderRadius":"12rpx","flexWrap":"wrap","borderWidth":"0 0 0px 0","display":"flex","width":"48%","borderStyle":"solid","height":"auto"}'>
						<image :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx 12rpx 0 0","display":"block","height":"320rpx"}' class="list-item-image" mode="aspectFill" v-if="product.xiangmutupian.substring(0,4)=='http'" :src="product.xiangmutupian"></image>
						<image :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx 12rpx 0 0","display":"block","height":"320rpx"}' class="list-item-image" mode="aspectFill" v-else :src="product.xiangmutupian?baseUrl+product.xiangmutupian.split(',')[0]:''"></image>
						<view class="list-item-body" :style='{"width":"100%","padding":"0 12rpx 0 12rpx","margin":"0","height":"auto"}'>
							<view :style='{"padding":"4rpx 0 4rpx 0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"600"}' class="list-item-title">{{product.xiangmubianhao}}</view>
							<view :style='{"padding":"4rpx 0 4rpx 0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"600"}' class="list-item-title">项目名称:{{product.xiangmumingcheng}}</view>
							<view :style='{"padding":"4rpx 0 4rpx 0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"600"}' class="list-item-title">{{product.fuwuleixing}}</view>
							<view :style='{"padding":"0 10rpx 0 0","order":"5"}'>
								<text class="icon iconfont icon-shijian21" :style='{"color":"#666","margin":"0 4rpx 0 0","lineHeight":"50rpx"}'></text>
								<text :style='{"color":"#666","lineHeight":"50rpx"}'>{{product.addtime.split(' ')[0].replace(/\-/g,'-')}}</text>
							</view>
							<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"1"}'>
								<text class="icon iconfont icon-zan10" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#666"}'></text>
								<text :style='{"color":"#666","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.thumbsupnum}}</text>
							</view>
							<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"2"}'>
								<text class="icon iconfont icon-shoucang10" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#666"}'></text>
								<text :style='{"color":"#666","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.storeupnum}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 商品推荐 -->
			
			<!-- 商品列表 -->
			<!-- 商品列表 -->
			<!-- 新闻资讯 -->
			<view class="listBox news" :style='{"width":"100%","margin":"0 0 20rpx","background":"#ffffff","order":"8"}'>
				<view class="title" :style='{"width":"100%","padding":"0","margin":"0 0 30rpx 0","background":"none","justifyContent":"space-between","display":"flex"}'>
					<view :style='{"padding":"0 0 0 10rpx","borderColor":"#ff6f9f","color":"#ff6f9f","borderWidth":"0 0 0 4rpx","fontSize":"36rpx","lineHeight":"1","borderStyle":"solid"}'>通知公告</view>
					<view :style='{"alignItems":"center","justifyContent":"center","display":"flex"}' @tap="onPageTap('news')">
					  <text :style='{"color":"#ff6f9f","fontSize":"28rpx","textDecoration":"underline"}'>MORE</text>
					  <text class="icon iconfont icon-jinru" :style='{"color":"#666","fontSize":"28rpx","display":"none"}'></text>
					</view>
				</view>
				<!-- 样式8 -->
				<view class="news-box5" :style='{"padding":"0","margin":"0","flexWrap":"wrap","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
					<view @tap="onNewsDetailTap(item.id)" v-for="(item,index) in news" :key="index" class="list-item" :style='{"padding":"20rpx","boxShadow":"0 2rpx 12rpx #cccccc","margin":"0 0 30rpx 0","borderColor":"#dddddd","borderRadius":"12rpx","background":"#fff","borderWidth":"0 0 0px 0","width":"100%","borderStyle":"solid","height":"auto"}'>
						<view :style='{"padding":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"600"}' class="title ">{{item.title}}</view>
						<view :style='{"padding":"0","margin":"0","overflow":"hidden","color":"#666","maxHeight":"144rpx","width":"100%","lineHeight":"48rpx","fontSize":"28rpx"}' class="text">{{item.introduction}}</view>
						<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"5"}'>
							<text class="icon iconfont icon-shijian21" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#666"}'></text>
							<text :style='{"color":"#666","lineHeight":"50rpx","fontSize":"24rpx"}'>{{item.addtime.split(' ')[0].replace(/\-/g,'-')}}</text>
						</view>
						<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"4"}'>
							<text class="icon iconfont icon-geren16" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#666"}'></text>
							<text :style='{"color":"#666","lineHeight":"50rpx","fontSize":"24rpx"}'>{{item.name}}</text>
						</view>
					</view>
				</view>
			</view>
			<!-- 新闻资讯 -->
			<view v-if="scrollTop>200" @tap="scrollTopClick" :style='{"boxShadow":"0 8rpx 16rpx rgba(0,0,0,.3)","borderRadius":"50%","textAlign":"center","bottom":"20%","background":"#ff000030","width":"60rpx","lineHeight":"60rpx","position":"fixed","right":"20rpx","height":"60rpx","zIndex":"999"}'>
				<span class="icon iconfont icon-jiantou07" :style='{"color":"#fff"}'></span>
			</view>
		</view>
	</view>
</template>

<script>
    import menu from '@/utils/menu'
	import '@/assets/css/global-restaurant.css'
	import uniIcons from "@/components/uni-ui/lib/uni-icons/uni-icons.vue"
	export default {
		components: {
			uniIcons
		},
		data() {
			return {
				startX: 0,
				prevNumList4: '',
				numList4: 0,
				timerList4: null,
				flagList4: false,
				navigationActive: {"width":"16rpx","margin":"0 4rpx","borderRadius":"100%","background":"#ff6f9f","height":"16rpx"},
				navigationDefault: {"width":"16rpx","margin":"0 4rpx","borderRadius":"100%","background":"#fff","height":"16rpx"},
				options2: {
					effect: 'flip',
					loop : true
				},
				options3: {
					effect: 'cube',
					loop : true,
					cubeEffect: {
						shadow: true,
						slideShadows: true,
						shadowOffset: 20,
						shadowScale: 0.94,
					}
				},
				rows: 2,
				column: 4,
				iconArr: [
					'cuIcon-same',
					'cuIcon-deliver',
					'cuIcon-evaluate',
					'cuIcon-shop',
					'cuIcon-ticket',
					'cuIcon-cascades',
					'cuIcon-discover',
					'cuIcon-question',
					'cuIcon-pic',
					'cuIcon-filter',
					'cuIcon-footprint',
					'cuIcon-pulldown',
					'cuIcon-pullup',
					'cuIcon-moreandroid',
					'cuIcon-refund',
					'cuIcon-qrcode',
					'cuIcon-remind',
					'cuIcon-profile',
					'cuIcon-home',
					'cuIcon-message',
					'cuIcon-link',
					'cuIcon-lock',
					'cuIcon-unlock',
					'cuIcon-vip',
					'cuIcon-weibo',
					'cuIcon-activity',
					'cuIcon-friendadd',
					'cuIcon-friendfamous',
					'cuIcon-friend',
					'cuIcon-goods',
					'cuIcon-selection'
				],
				role : '',
				menuList: [],
				swiperMenuList:[],
				user: {},
				tableName:'',
				menuColor: '#02d273,#fe7991,#fbd34d,#f08a31,#f56c6c,#00d07f,#81b63d,#f093a9,#a7c23a,#34c7b7,#1bb468,#fd7c94,#ff758c'.split(','),

				//轮播
				swiperList: [],
				fuwuxinxilist: [],
				news: [],
				scrollTop: 0,
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
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
		},
		async onLoad(){
			this.menuColor = this.menuColor.sort(()=> {
				return (0.5-Math.random());
			});
		},
		async onShow() {
			if (this.timerList4&&this.timerList4!=null) clearInterval(this.timerList4)
			this.swiperMenuList = []
			this.role = uni.getStorageSync("appRole");
			let table = uni.getStorageSync("nowTable");
			let res = {}
			if(table) {
				res = await this.$api.session(table);
				this.user = res.data;
				this.tableName = table;
			}
			let menus = menu.list();
			this.menuList = menus;
			this.menuList.forEach((item,key) => {
				if(key==0) {
					item.frontMenu.forEach((item2,key2) => {
						if(item2.child[0].buttons.indexOf("查看")>-1) {
							this.swiperMenuList.push(item2);
						}
					})
				}
			})
			// let res;
			// 轮播图
			let swiperList = []
			res = await this.$api.list('config', {
				type: 1,
				limit: 100
			});
			for (let item of res.data.list) {
				if (item.name.indexOf('picture') >= 0 && item.value && item.value!="" && item.value!=null ) {
					swiperList.push({
						img: item.value,
						title: item.name,
						url: item.url
					});
				}
			}
			if (swiperList) {
				this.swiperList = swiperList;
			}
			
			this.prevNumList4 = this.swiperList.length - 1
			this.timerList4 = setInterval(this.autoPlayList4, 6000)

			// 推荐信息
			this.getRecommendList()
			this.getHomeList()
			this.getNewsList()
		},
		methods: {
			scrollTopClick(){
				uni.pageScrollTo({
					scrollTop: 0
				})
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
			async getNewsList(){
				let res;
				let params = {
					page: 1,
					limit: 6,
					sort: 'id',
					order: 'desc',
				}
				// 通知公告
				res = await this.$api.list('news', params)
				this.news = res.data.list
			},
			homeTabClick2(index,name){
				this['home' + name + 'Index2'] = index
				this.getHomeList()
			},
			async getHomeList(){
				let res;
				let params;
			},
			recommendTabClick2(index,name){
				this[name + 'Index2'] = index
				this.getRecommendList()
			},
			async getRecommendList(){
				let res;
				let params;
				// 推荐信息
				params = {
					page: 1,
					limit: 4,
					onshelves: 1,
				}
				if(uni.getStorageSync("appUserid")) {
					res = await this.$api.recommend2('fuwuxinxi', params);
				} else {
					res = await this.$api.recommend('fuwuxinxi', params);
				}
				this.fuwuxinxilist = res.data.list
				

			},
			autoPlayList4() {
				this.prevNumList4 = this.numList4
			
				this.numList4++
				if (this.numList4 == this.swiperList.length) this.numList4 = 0
			},
			touchStart(event) {
				this.startX = event.touches[0].clientX
				
				clearInterval(this.timerList4)
				this.flagList4 = true
			},
			touchMove(event) {
				const currentX = event.touches[0].clientX;
				const deltaX = currentX - this.startX;
				
				if (deltaX > 50) {
					// 向右滑动逻辑
					if (this.flagList4) {
						this.flagList4 = false
						
						this.prevNumList4 = this.numList4
						this.numList4++
						if (this.numList4 == this.swiperList.length) this.numList4 = 0
					}
					
				} else if (deltaX < -50) {
					// 向左滑动逻辑
					if (this.flagList4) {
						this.flagList4 = false
						
						this.prevNumList4 = this.numList4
						this.numList4--
						if (this.numList4 < 0) this.numList4 = this.swiperList.length - 1
					}
				}
			},
			touchEnd() {
				this.startX = 0
				this.timerList4 = setInterval(this.autoPlayList4, 6000)
				this.flagList4 = false
			},
			//轮播图跳转
			onSwiperTap(e) {
				if(e.url) {
					if (e.url.indexOf('https') != -1) {
						// #ifdef MP-WEIXIN
						uni.navigateTo({
						    url: '../../common/linkOthers/linkOthers?url=' + encodeURIComponent(e.url),
						});
						return false
						// #endif
						window.open(e.url)
					} else {
						this.$utils.jump(e.url)
					}
				}
			},
			// 新闻详情
			onNewsDetailTap(id) {
				this.$utils.jump(`../news-detail/news-detail?id=${id}`)
			},
			// 推荐列表点击详情
			onDetailTap(tableName, id) {
				let url = `../${tableName}/detail?id=${id}`
				this.$utils.jump(url)
			},
			onPageTap(tableName){
				let url = `../${tableName}/list`
				uni.navigateTo({
					url: url,
					fail: function(){
						uni.switchTab({
							url: url
						});
					}
				});
			},
			onPageTap2(index) {
				let url = '../' + index + '/list'
				if(index=='kefuguanli') {
					url = '../chat/chat'
				}
				uni.setStorageSync("useridTag",0);
				uni.navigateTo({
					url: url,
					fail: function() {
						uni.switchTab({
							url: url
						});
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	.list-swiper-4 .animate__animated {
		--animate-delay: 300ms;
	}

	.menu_view {
		padding: 0;
		margin: 20rpx 0 20rpx 0;
		background: none;
		display: flex;
		width: 100%;
		flex-wrap: wrap;
		height: auto;
		order: 1;
		.menu-item {
			padding: 12rpx 0 12rpx 0;
			margin: 0 0 0 0;
			width: 33.33%;
			border-color: #ffffff50;
			border-width: 2rpx 2rpx 2rpx 2rpx;
			border-style: solid;
			height: auto;
			.iconarr {
				border-radius: 100%;
				padding: 0;
				margin: 0 auto 0 auto;
				color: #fff;
				background: none;
				display: block;
				width: 104rpx;
				font-size: 80rpx;
				line-height: 104rpx;
				text-align: center;
				height: 104rpx;
			}
			.text {
				padding: 0;
				margin: 12rpx auto 0 auto;
				color: #fff;
				white-space: nowrap;
				width: 100%;
				font-size: 28rpx;
				line-height: 28rpx;
				text-align: center;
			}
		}
	}
</style>
