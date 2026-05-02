
<template>
<view>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
	<view class="content">
		<view class="container" :style='{"minHeight":"100%","width":"100%","padding":"24rpx","position":"relative","background":"#fff","height":"auto"}'>
			<swiper :style='{"width":"100%","height":"440rpx"}' class="swiper" :indicator-dots='false' :autoplay='false' :circular='false' indicator-active-color='#000000' indicator-color='rgba(0, 0, 0, .3)' :duration='500' :interval='5000' :vertical='false'>
				<swiper-item :style='{"width":"100%","borderRadius":"12rpx","background":"#fff","height":"440rpx"}' v-for="(swiper,index) in swiperList" :key="index">
					<img :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"440rpx"}' v-if="swiper.substring(0,4)=='http'" :src="swiper" @tap="imgView(swiper)">
					<img :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"440rpx"}' v-else :src="baseUrl+swiper" @tap="imgView(baseUrl+swiper)">
				</swiper-item>
			</swiper>
			<view :style='{"alignContent":"flex-start","padding":"24rpx 0 24rpx 0","alignItems":"flex-start","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","height":"auto"}' class="detail-content">

				<view :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="detail-list-item title">
					<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}' class="lable">预约编号：</view>
					<view :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' class="text" >{{detail.yuyuebianhao}}</view>
				</view>
				<view :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="detail-list-item title">
					<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}' class="lable">项目编号：</view>
					<view :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' class="text" >{{detail.xiangmubianhao}}</view>
				</view>
				<view :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="detail-list-item title">
					<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}' class="lable">项目名称：</view>
					<view :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' class="text" >{{detail.xiangmumingcheng}}</view>
				</view>

				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>取消编号：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.quxiaobianhao}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>服务类型：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.fuwuleixing}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>服务费用：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.fuwufeiyong}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>预约日期：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.yuyueriqi}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>预约时段：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.yuyueshiduan}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>用户账号：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.yonghuzhanghao}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>用户姓名：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.yonghuxingming}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>手机号：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' style="text-decoration: underline" @tap="callClick(detail.shoujihao)" >{{detail.shoujihao}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>取消时间：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.quxiaoshijian}}</view>
				</view>



				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>取消说明：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}'>{{detail.quxiaoshuoming}}</view>
				</view>

				<view v-if="userid" class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>审核状态：</view>
					<view v-if="detail.sfsh=='是'" class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}'>通过</view>
					<view v-if="detail.sfsh=='否'" class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}'>不通过</view>
					<view v-if="detail.sfsh=='待审核'" class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}'>待审核</view>
				</view>
				<view v-if="userid" class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>审核回复</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}'>{{detail.shhf}}</view>
				</view>




				<view class="bottom-content bg-white tabbar border shop" :style='{"padding":"0","margin":"20rpx 0 0 0","flexWrap":"wrap","background":"none","display":"flex","width":"100%","height":"auto","order":"60"}'>

					<button :style='{"border":"2rpx solid #33993310","padding":"0 20rpx 0 20rpx","margin":"0 10rpx 10rpx 0","color":"#339933","borderRadius":"20rpx","background":"#33993320","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' @tap="onPayTap" v-if="userid&&isAuth('quxiaoyuyue','支付') && detail.ispay!='已支付'"  class="cu-btn bg-brown round shadow-blur">支付</button>
					<button :style='{"border":"2rpx solid #33993310","padding":"0 20rpx 0 20rpx","margin":"0 10rpx 10rpx 0","color":"#339933","borderRadius":"20rpx","background":"#33993320","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' @tap="onPayTap" v-if="!userid&&isAuthFront('quxiaoyuyue','支付') && detail.ispay!='已支付'"  class="cu-btn bg-brown round shadow-blur">支付</button>
					<button :style='{"border":"2rpx solid #33993310","padding":"0 20rpx 0 20rpx","margin":"0 10rpx 10rpx 0","color":"#339933","borderRadius":"20rpx","background":"#33993320","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' v-if="!isAuth('quxiaoyuyue','支付') && detail.ispay!='已支付'" class="cu-btn bg-brown round shadow-blur">未支付</button>
					<button :style='{"border":"2rpx solid #33993310","padding":"0 20rpx 0 20rpx","margin":"0 10rpx 10rpx 0","color":"#339933","borderRadius":"20rpx","background":"#33993320","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' v-if="detail.ispay=='已支付'"  class="cu-btn bg-brown round shadow-blur">已支付</button>
					<button :style='{"border":"2rpx solid #c0755410","padding":"0 20rpx","margin":"0 10rpx 10rpx 0","color":"#c07554","borderRadius":"20rpx","background":"#c0755420","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' v-if="userid&&isAuth('quxiaoyuyue','审核')" @tap="onSHTap">审核</button>
					<button :style='{"border":"2rpx solid #c0755410","padding":"0 20rpx","margin":"0 10rpx 10rpx 0","color":"#c07554","borderRadius":"20rpx","background":"#c0755420","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' v-if="!userid&&isAuthFront('quxiaoyuyue','审核')" @tap="onSHTap">审核</button>
				</view>
			</view>
		</view>
		<!-- 确认完成弹窗 -->
		<uni-popup class="popup-content" ref="popup" type="center">
			<form class="popup-form" style="background: #fff;width: 300px;display: block;border-radius: 10px;padding: 20px;text-align: center;">
				<view class=" margin-top" style="border: 1px solid #666;line-height: 30px;border-radius: 5px;margin-bottom: 10px">
					<picker @change="sfshChange" :value="sfshIndex" :range="sfshOptions">
						<view class="uni-input">{{detail.sfsh?detail.sfsh:'选择审核结果'}}</view>
					</picker>
				</view>
				<view class="" style="border: 1px solid #666;border-radius: 5px;margin-bottom: 10px;text-align: left;padding: 10px">
					<!-- <view class="title">审核回复</view> -->
					<textarea style="width: 200px;" v-model="detail.shhf" placeholder="审核回复"></textarea>
					<!-- <input v-model="douyinId" placeholder="审核回复" name="input"></input> -->
				</view>
				<view class="btn-content">
					<button style="margin: 0 10px" @tap="onCloseWinTap" class="cu-btn bg-cyan">取消</button>
					<button style="margin: 0 10px" @tap="onFinishTap" class="cu-btn bg-red">确认</button>
				</view>
			</form>
		</uni-popup>
	</view>
</mescroll-uni>
</view>
</template>

<script>
	export default {
		data() {
			return {
				btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
				id: '',
				userid: '',
				detail: {},
				swiperList: [],
				commentList: [],
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
					onScroll: true,
					toTop: true
				},
				hasNext: true,
				user: {},
				sfshIndex: -1,
				sfshOptions: ['待审核','通过','不通过'],
				count: 0,
				timer: null,
				title:'',
				scrollTop: 0,
			}
		},
		components: {
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			username() {
				return uni.getStorageSync("nickname")
			},
		},
		async onLoad(options) {
			// #ifdef APP-PLUS
			let page = getCurrentPages()
			this.href = this.baseUrl + 'front/h5/#/' + page[page.length - 1].route
			// #endif
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			this.id = options.id;
			if(options.userid) {
				this.userid = options.userid;
			}
			// 渲染数据
			this.init();
		},
		// #ifdef MP-WEIXIN
		onShareAppMessage(){
			var obj = {
				title: this.title,
				imageUrl: this.swiperList[0]?this.baseUrl + this.swiperList[0]: ''
			}
			return obj
		},
		// #endif
		onUnload() {
			if(this.timer) {
				clearInterval(this.timer);
			}
		},
		async onShow(options) {
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			this.btnColor = this.btnColor.sort(()=> {
				return (0.5-Math.random());
			});
			let crossCleanType = uni.getStorageSync('crossCleanType')
			if(crossCleanType) {
				uni.removeStorageSync('crossCleanType')
				res = await this.$api.info('quxiaoyuyue', this.id);
				let reg=new RegExp('http://localhost:8080/springcloud-alibaba/upload','g')//g代表全部
				this.detail = res.data;
				this.title = this.detail.yuyuebianhao
			}
			let payType = uni.getStorageSync('payType')
			if(payType){
				uni.removeStorageSync('payType')
				this.mescroll.num = 1
				this.upCallback(this.mescroll)
				this.init();
			}
		},
		destroyed: function() {
			//window.clearInterval(this.inter);
		},
		methods: {
			scrollChange(e){
				this.scrollTop = e.scrollTop
			},
			scrollTopClick(){
				uni.pageScrollTo({
					scrollTop: 0
				})
			},
			imgView(url){
				let arr = []
				for(let x in this.swiperList){
					arr.push(this.swiperList[x].substr(0,4)=='http'?this.swiperList[x]:this.baseUrl + this.swiperList[x])
				}
				uni.previewImage({
					current: url,
					urls: arr
				})
			},
			// 拨打电话
			callClick(row){
				uni.makePhoneCall({
					phoneNumber: row
				})
			},
			// 支付
			onPayTap(){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				if(this.detail.sfsh!='是') {
					this.$utils.msg("请审核通过后再操作");
					return
				}
				uni.setStorageSync('paytable','quxiaoyuyue');
				uni.setStorageSync('payObject',this.detail);
				this.$utils.jump('../pay-confirm/pay-confirm?type=1')
			},
			onDetailTap(item) {
				uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
			},
			// 跨表
			async onAcrossTap(tableName,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue,type=1){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				if(crossOptAudit=='是'&&this.detail.sfsh!='是') {
					this.$utils.msg("请审核通过后再操作");
					return
				}
				if(crossOptPay=='是'&&this.detail.ispay!='已支付') {
					this.$utils.msg('请支付完成再操作')
					return
				}
				uni.setStorageSync('crossTable','quxiaoyuyue');
				uni.setStorageSync(`crossObj`, this.detail);
				uni.setStorageSync(`statusColumnName`, statusColumnName);
				uni.setStorageSync(`statusColumnValue`, statusColumnValue);
				uni.setStorageSync(`tips`, tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = uni.getStorageSync('crossObj');
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$utils.msg(tips);
							return
						}
					}
				}
				this.$utils.jump(`../${tableName}/add-or-update?cross=true`);
			},
			// 获取详情
			async init(type=1){
				if(this.timer) {
					clearInterval(this.timer);
				}
				let res = await this.$api.info('quxiaoyuyue', this.id);
				let reg=new RegExp('http://localhost:8080/springcloud-alibaba/upload','g')//g代表全部
				this.detail = res.data;

				this.title = this.detail.yuyuebianhao




				// 轮播图片
				this.swiperList = this.detail.xiangmutupian ? this.detail.xiangmutupian.split(",") : [];
				













				if(type==2){
					this.detail.discussnum++
					await this.$api.update('quxiaoyuyue',this.detail)
				}
			},
			// mescroll组件初始化的回调,可获取到mescroll对象
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},

			/*下拉刷新的回调 */
			downCallback(mescroll) {
				this.hasNext = true
				mescroll.resetUpScroll()
			},

			/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
			async upCallback(mescroll) {
				mescroll.endSuccess(mescroll.size, this.hasNext);

			},



			onChatTap() {
				this.$utils.jump('../chat/chat')
			},
			// 下载
			download(url ){
				if(!url){
					return false
				}
				let _this = this;
				url=_this.$base.url +  url;
				uni.downloadFile({
					url: url,
					success: (res) => {
						if (res.statusCode === 200) {
							_this.$utils.msg('下载成功');
							// #ifdef H5
							 window.open(url);
							// #endif
							// #ifndef H5
							uni.saveFile({
								tempFilePath: res.tempFilePath, //临时路径
								success: function(obj) {
									uni.showToast({
										icon: 'success',
										mask: true,
										title: '下载成功' , 
										duration: 2000,
									});
									setTimeout(() => {
										console.log('obj.savedFilePath',obj.savedFilePath);
										var filePath = obj.savedFilePath;
										uni.openDocument({ //新开页面打开文档，支持格式：doc, xls, ppt, pdf, docx, xlsx, pptx。
											filePath: filePath,
											showMenu: true,
											success: function(res) {
												console.log('打开文档成功');
											}
										});
									}, 2000)
								}
							});
							// #endif
						}
					}
				});
			},
			//
			onCartTabTap() {
				this.$utils.tab('../shop-cart/shop-cart')
			},
			onSHTap() {
				if(this.detail.sfsh=='是'||this.detail.sfsh=='否') {
					this.$utils.msg('已审核完成');
					return;
				}
				this.$refs.popup.open()
			},
			// 完成审核
			async onFinishTap() {
				if(!this.detail.sfsh){
					this.$utils.msg('请选择审核状态');
					return
				}
				if(!this.detail.shhf){
					this.$utils.msg('请填写审核回复');
					return
				}
				if(this.detail.sfsh=="通过"){
					this.detail.sfsh = '是'
				}
				if(this.detail.sfsh=="不通过"){
					this.detail.sfsh = '否'
				}
				if(this.detail.sfsh=="待审核"){
					this.detail.sfsh = '待审核'
				}
				if(this.detail.sfsh=='是') {
					await this.$api.postPublic('deleteColumn/fuwuyuyue',{
						csuConditionColumn: 'yuyuebianhao',
						csuConditionColumnValue: this.detail.yuyuebianhao
					})
				}
				if(this.detail.sfsh=='是') {
					let ott = await this.$api.postPublic('updateColumn/fuwuxinxi/2',{
						csuUpdateColumn: 'xiaoliang',
						csuUpdateColumnValue: '-1',
						csuConditionColumn: 'xiangmubianhao',
						csuConditionColumnValue: this.detail.xiangmubianhao
					})
					if(ott.code!=0) {
						that.$utils.msg('更改失败');
						return false
					}
				}
				await this.$api.update('quxiaoyuyue', this.detail);
				this.$utils.msg('审核成功');
				this.$refs.popup.close();
			},
			// 关闭窗口
			onCloseWinTap() {
				this.$refs.popup.close()
			},
			sfshChange(e){
				this.sfshIndex = e.target.value
				this.detail.sfsh = this.sfshOptions[this.sfshIndex]
				this.$forceUpdate()
			},

		}
	}
</script>

<style lang="scss">
	page {
	  --animate-duration: 1s;
	  --animate-delay: 1s;
	  --animate-repeat: 1;
	}
	
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	
	.seat-list {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		background: #FFFFFF;
		margin: 20upx;
		border-radius: 20upx;
		padding: 20upx;
		font-size: 30upx;
		.seat-item {
			width: 33.33%;
			display: flex;
			align-items: center;
			flex-direction: column;
			margin-bottom: 20upx;
	
			.seat-icon {
				width: 50upx;
				height: 50upx;
				margin-bottom: 10upx;
			}
		}
	}
	
	audio {
		display: flex;
		flex-direction: column;
	}
	
	.audio .uni-audio-default {
		width: inherit !important;
	}
	

</style>
