  

<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#fff","height":"auto"}' v-if="showType==1">
		<form :style='{"width":"100%","padding":"24rpx 48rpx 24rpx 48rpx","background":"#fff","display":"block","height":"auto"}' class="app-update-pv">


































			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">预约编号</view>
				<view :style='{"padding":"0 24rpx 0 24rpx","margin":"0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","flex":"1"}' class="right-input">
					{{ruleForm.yuyuebianhao}}
				</view>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目编号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xiangmubianhao" v-model="ruleForm.xiangmubianhao" placeholder="项目编号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目名称</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xiangmumingcheng" v-model="ruleForm.xiangmumingcheng" placeholder="项目名称"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="xiangmutupianTap">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目图片</view>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.xiangmutupian" :src="baseUrl+ruleForm.xiangmutupian.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务类型</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwuleixing" v-model="ruleForm.fuwuleixing" placeholder="服务类型"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务费用</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwufeiyong" v-model="ruleForm.fuwufeiyong" placeholder="服务费用" type="digit" @input="(e)=>numberChange(e,'fuwufeiyong')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务内容</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwuneirong" v-model="ruleForm.fuwuneirong" placeholder="服务内容"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">预约日期</view>
				<input :disabled="ro.yuyueriqi" :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-model="ruleForm.yuyueriqi" placeholder="预约日期" @tap="toggleTab('yuyueriqi')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">预约时段</view>
				<picker :disabled="ro.yuyueshiduan" :style='{"width":"100%","flex":"1","height":"auto"}' @change="yuyueshiduanChange" :value="yuyueshiduanIndex" :range="yuyueshiduanOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#666"}' class="uni-input">{{ruleForm.yuyueshiduan?ruleForm.yuyueshiduan:"请选择预约时段"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">预约时间</view>
				<input :disabled="ro.yuyueshijian" :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-model="ruleForm.yuyueshijian" placeholder="预约时间" @tap="toggleTab('yuyueshijian')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">订单状态</view>
				<picker :disabled="ro.dingdanzhuangtai" :style='{"width":"100%","flex":"1","height":"auto"}' @change="dingdanzhuangtaiChange" :value="dingdanzhuangtaiIndex" :range="dingdanzhuangtaiOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#666"}' class="uni-input">{{ruleForm.dingdanzhuangtai?ruleForm.dingdanzhuangtai:"请选择订单状态"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">用户账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yonghuzhanghao" v-model="ruleForm.yonghuzhanghao" placeholder="用户账号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">用户姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yonghuxingming" v-model="ruleForm.yonghuxingming" placeholder="用户姓名"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">手机号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.shoujihao" v-model="ruleForm.shoujihao" placeholder="手机号"  type="text"></input>
			</view>
      			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">位置</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' readonly @tap="fulladdressTap('weizhi')" v-model="ruleForm.weizhi2" placeholder="位置" :maxlength="-1"></input>
			</view>
         			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务地址</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' readonly @tap="fulladdressTap('fuwudizhi')" v-model="ruleForm.fuwudizhi2" placeholder="服务地址" :maxlength="-1"></input>
			</view>
    
			
			<view :style='{"width":"100%","padding":"0","margin":"20rpx 0 40rpx 0","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 30rpx 30rpx 30rpx","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"30rpx","minWidth":"220rpx","height":"88rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker :starttime="yuyueriqistarttime" mode="date" step="1" :current="false" :hasSecond="false" @confirm="yuyueriqiConfirm" ref="yuyueriqi" themeColor="#333333"></w-picker>
		<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="yuyueshijianConfirm" ref="yuyueshijian" themeColor="#333333"></w-picker>
	</view>
	<view v-else-if="showType==2">
		<view style="width: 100%;display: flex;flex-wrap: wrap;padding: 10px;">
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuyuyue','预约金额统计'))||(!userid&&isAuthFront('fuwuyuyue','预约金额统计'))" size="mini" type="primary" @click="chartClick1()">
				预约金额统计
			</button>
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuyuyue','预约服务类型统计'))||(!userid&&isAuthFront('fuwuyuyue','预约服务类型统计'))" size="mini" type="primary" @click="chartClick2()">
				预约服务类型统计
			</button>
		</view>
		<view style="width: 100%;">
			<view v-if="chartType1" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				预约金额统计
			</view>
			<qiun-data-charts
				v-if="chartType1"
				style="width: 100%;height:"
				type="line"
				:opts="opts1"
				:chartData="chartData1">
			</qiun-data-charts>
			<view v-if="chartType2" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				预约服务类型统计
			</view>
			<qiun-data-charts
				v-if="chartType2"
				style="width: 100%;height:"
				type="area"
				:opts="opts2"
				:chartData="chartData2">
			</qiun-data-charts>
		</view>
	</view>
</view>
</template>

<script>
	import wPicker from "@/components/w-picker/w-picker.vue";
	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	import qiunDataCharts from '@/components/qiun-data-charts/components/qiun-data-charts/qiun-data-charts.vue';
	export default {
		data() {
			return {
				cross:'',
				ruleForm: {
				yuyuebianhao: this.getUUID(),
				xiangmubianhao: '',
				xiangmumingcheng: '',
				xiangmutupian: '',
				fuwuleixing: '',
				fuwufeiyong: '',
				weizhi: '',
				fuwuneirong: '',
				yuyueriqi: '',
				yuyueshiduan: '',
				yuyueshijian: '',
				dingdanzhuangtai: '预约中' ,
				yonghuzhanghao: '',
				yonghuxingming: '',
				fuwudizhi: '',
				shoujihao: '',
				ispay: '',
				},
				yuyueriqistarttime: '',
				yuyueshiduanOptions: [],
				yuyueshiduanIndex: 0,
				dingdanzhuangtaiOptions: [],
				dingdanzhuangtaiIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   yuyuebianhao : false,
				   xiangmubianhao : false,
				   xiangmumingcheng : false,
				   xiangmutupian : false,
				   fuwuleixing : false,
				   fuwufeiyong : false,
				   weizhi : false,
				   fuwuneirong : false,
				   yuyueriqi : false,
				   yuyueshiduan : false,
				   yuyueshijian : false,
				   dingdanzhuangtai : false,
				   yonghuzhanghao : false,
				   yonghuxingming : false,
				   fuwudizhi : false,
				   shoujihao : false,
				   ispay : false,
				},
				virtualPay: false,
				showType: 1,
				userid: '',
				chartData1: {},
				opts1: {},
				chartType1: false,
				chartData2: {},
				opts2: {},
				chartType2: false,
			}
		},
		components: {
			wPicker,
			xiaEditor,
			multipleSelect,
			qiunDataCharts,
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			sessionForm() {
				return uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
			},



		},
		async onLoad(options) {
			if(options.showType) {
				this.showType = 2
				if(options.userid&&options.userid!='') {
					this.userid = options.userid
				}
				return false
			}else {
				this.showType = 1
			}
			if(options.virtualPay){
				this.virtualPay = true
			}
			this.yuyueriqistarttime = this.$utils.getCurDate()
			this.ruleForm.yuyueshijian =  this.$utils.getCurDateTime();
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取
			this.ruleForm.yonghuzhanghao = this.user.yonghuzhanghao
			this.ro.yonghuzhanghao = true;
			this.ruleForm.yonghuxingming = this.user.yonghuxingming
			this.ro.yonghuxingming = true;

			this.ro.yuyueshijian = true;
			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
					if(o=='yuyuebianhao'){
						this.ruleForm.yuyuebianhao = obj[o];
						this.ro.yuyuebianhao = true;
						continue;
					}
					if(o=='xiangmubianhao'){
						this.ruleForm.xiangmubianhao = obj[o];
						this.ro.xiangmubianhao = true;
						continue;
					}
					if(o=='xiangmumingcheng'){
						this.ruleForm.xiangmumingcheng = obj[o];
						this.ro.xiangmumingcheng = true;
						continue;
					}
					if(o=='xiangmutupian'){
						this.ruleForm.xiangmutupian = obj[o].split(",")[0];
						this.ro.xiangmutupian = true;
						continue;
					}
					if(o=='fuwuleixing'){
						this.ruleForm.fuwuleixing = obj[o];
						this.ro.fuwuleixing = true;
						continue;
					}
					if(o=='fuwufeiyong'){
						this.ruleForm.fuwufeiyong = obj[o];
						this.ro.fuwufeiyong = true;
						continue;
					}
					if(o=='weizhi'){
						this.ruleForm.weizhi2 = obj[o].split(';')[2]
						this.ruleForm.weizhi = obj[o];
						this.ro.weizhi = true;
						continue;
					}
					if(o=='fuwuneirong'){
						this.ruleForm.fuwuneirong = obj[o];
						this.ro.fuwuneirong = true;
						continue;
					}
					if(o=='yuyueriqi'){
						this.ruleForm.yuyueriqi = obj[o];
						this.ro.yuyueriqi = true;
						continue;
					}
					if(o=='yuyueshiduan'){
						this.ruleForm.yuyueshiduan = obj[o];
						for(let x in this.yuyueshiduanOptions) {
							if(this.yuyueshiduanOptions[x] == this.ruleForm.yuyueshiduan) {
								this.yuyueshiduanIndex = Number(x)
							}
						}
						this.ro.yuyueshiduan = true;
						continue;
					}
					if(o=='yuyueshijian'){
						this.ruleForm.yuyueshijian = obj[o];
						this.ro.yuyueshijian = true;
						continue;
					}
					if(o=='dingdanzhuangtai'){
						this.ruleForm.dingdanzhuangtai = obj[o];
						for(let x in this.dingdanzhuangtaiOptions) {
							if(this.dingdanzhuangtaiOptions[x] == this.ruleForm.dingdanzhuangtai) {
								this.dingdanzhuangtaiIndex = Number(x)
							}
						}
						this.ro.dingdanzhuangtai = true;
						continue;
					}
					if(o=='yonghuzhanghao'){
						this.ruleForm.yonghuzhanghao = obj[o];
						this.ro.yonghuzhanghao = true;
						continue;
					}
					if(o=='yonghuxingming'){
						this.ruleForm.yonghuxingming = obj[o];
						this.ro.yonghuxingming = true;
						continue;
					}
					if(o=='fuwudizhi'){
						this.ruleForm.fuwudizhi2 = obj[o].split(';')[2]
						this.ruleForm.fuwudizhi = obj[o];
						this.ro.fuwudizhi = true;
						continue;
					}
					if(o=='shoujihao'){
						this.ruleForm.shoujihao = obj[o];
						this.ro.shoujihao = true;
						continue;
					}
				}
			}

			// 自定义下拉框值
			this.yuyueshiduanOptions = "上午,下午".split(',')
			this.yuyueshiduanOptions.unshift("请选择预约时段");
			if(this.ruleForm.yuyueshiduan) {
				for(let x in this.yuyueshiduanOptions) {
					if(this.yuyueshiduanOptions[x] == this.ruleForm.yuyueshiduan) {
						this.yuyueshiduanIndex = Number(x)
					}
				}
			}
			// 自定义下拉框值
			this.dingdanzhuangtaiOptions = "已派单,已取消,预约中".split(',')
			this.dingdanzhuangtaiOptions.unshift("请选择订单状态");
			if(this.ruleForm.dingdanzhuangtai) {
				for(let x in this.dingdanzhuangtaiOptions) {
					if(this.dingdanzhuangtaiOptions[x] == this.ruleForm.dingdanzhuangtai) {
						this.dingdanzhuangtaiIndex = Number(x)
					}
				}
			}

			// 如果有登录，获取登录后保存的userid
			this.ruleForm.userid = uni.getStorageSync("appUserid")
			if (options.refid) {
				// 如果上一级页面传递了refid，获取改refid数据信息
				this.ruleForm.refid = Number(options.refid);
				this.ruleForm.nickname = uni.getStorageSync("nickname");
			}
			// 如果是更新操作
			if (options.id) {
				this.ruleForm.id = options.id;
				// 获取信息
				res = await this.$api.info(`fuwuyuyue`, this.ruleForm.id);
				res.data.weizhi2 = res.data.weizhi.split(';')[2]
				if(res.data.yuyueshiduan) {
					for(let x in this.yuyueshiduanOptions) {
						if(this.yuyueshiduanOptions[x] == res.data.yuyueshiduan) {
							this.yuyueshiduanIndex = Number(x)
						}
					}
				}
				if(res.data.dingdanzhuangtai) {
					for(let x in this.dingdanzhuangtaiOptions) {
						if(this.dingdanzhuangtaiOptions[x] == res.data.dingdanzhuangtai) {
							this.dingdanzhuangtaiIndex = Number(x)
						}
					}
				}
				res.data.fuwudizhi2 = res.data.fuwudizhi.split(';')[2]
				this.ruleForm = res.data;
			}
			this.$forceUpdate()
			if (uni.getStorageSync('raffleType') && uni.getStorageSync('raffleType') != null) {
				uni.removeStorageSync('raffleType')
				setTimeout(() => {
					this.onSubmitTap(null)
				}, 300)
			}
		},
		methods: {
			numberChange(e) {
				e = Number(e)
			},
			fulladdressTap (name='') {
				if(this.ro[name]) {
					return false
				}
				uni.chooseLocation({
					success: (data) => {
						if(name){
							this.ruleForm[name] = data.longitude + ';' + data.latitude + ';' + data.address
							this.ruleForm[name + 2] = data.address
						}else {
							this.ruleForm.fulladdress = data.address;
							this.ruleForm.longitude = data.longitude;
							this.ruleForm.latitude = data.latitude;
						}
						this.$forceUpdate()
					}
				})
			},

			// 多级联动参数

			yuyueriqiChange(e) {
				this.ruleForm.yuyueriqi = e.target.value;
				this.$forceUpdate();
			},

			// 日长控件选择日期时间
			yuyueriqiConfirm(val) {
				this.ruleForm.yuyueriqi = val.result;
				this.$forceUpdate();
			},
			// 日长控件选择日期时间
			yuyueshijianConfirm(val) {
				this.ruleForm.yuyueshijian = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			yuyueshiduanChange(e) {
				this.yuyueshiduanIndex = e.target.value
				this.ruleForm.yuyueshiduan = this.yuyueshiduanOptions[this.yuyueshiduanIndex]
			},
			// 下拉变化
			dingdanzhuangtaiChange(e) {
				this.dingdanzhuangtaiIndex = e.target.value
				this.ruleForm.dingdanzhuangtai = this.dingdanzhuangtaiOptions[this.dingdanzhuangtaiIndex]
			},

			xiangmutupianTap() {
				if(this.ro.xiangmutupian){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.xiangmutupian = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},

			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap(subMitType=null) {
				let that = this
				if(!this.ruleForm.id) {
					this.ruleForm.ispay = '未支付'
				}
				if(this.ruleForm.yuyuebianhao){
					this.ruleForm.yuyuebianhao = String(this.ruleForm.yuyuebianhao)
				}
				//跨表计算判断
				var obj;
				if((!this.ruleForm.yuyuebianhao)){
					this.$utils.msg(`预约编号不能为空`);
					return
				}
				if((!this.ruleForm.xiangmubianhao)){
					this.$utils.msg(`项目编号不能为空`);
					return
				}
				if((!this.ruleForm.xiangmumingcheng)){
					this.$utils.msg(`项目名称不能为空`);
					return
				}
				if(this.ruleForm.fuwufeiyong&&(!this.$validate.isNumber(this.ruleForm.fuwufeiyong))){
					this.$utils.msg(`服务费用应输入数字`);
					return
				}
				if((!this.ruleForm.yuyueriqi)){
					this.$utils.msg(`预约日期不能为空`);
					return
				}
				if((!this.ruleForm.yuyueshiduan)){
					this.$utils.msg(`预约时段不能为空`);
					return
				}
				if(this.ruleForm.shoujihao&&(!this.$validate.isMobile(this.ruleForm.shoujihao))){
					this.$utils.msg(`手机号应输入手机格式`);
					return
				}
				//更新跨表属性
				var crossuserid;
				var crossrefid;
				var crossoptnum;
				if(this.cross){
					var statusColumnName = uni.getStorageSync('statusColumnName');
					var statusColumnValue = uni.getStorageSync('statusColumnValue');
					if(statusColumnName!='') {
						if(!obj) {
							obj = uni.getStorageSync('crossObj');
						}
						if(!statusColumnName.startsWith("[")) {
							for (var o in obj){
								if(o==statusColumnName){
									obj[o] = statusColumnValue;
								}
							}
							var table = uni.getStorageSync('crossTable');
							await this.$api.update(`${table}`, obj);
						} else {
								crossuserid=Number(uni.getStorageSync('appUserid'));
								crossrefid=obj['id'];
								crossoptnum=uni.getStorageSync('statusColumnName');
								crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
						}
					}
					if(crossrefid && crossuserid) {
						this.ruleForm.crossuserid=crossuserid;
						this.ruleForm.crossrefid=crossrefid;
						let params = {
							page: 1,
							limit:10,
							crossuserid:crossuserid,
							crossrefid:crossrefid,
						}
						let res = await this.$api.list(`fuwuyuyue`, params);
						if (res.data.total >= crossoptnum) {
							this.$utils.msg(uni.getStorageSync('tips'));
							uni.removeStorageSync('crossCleanType');
							return false;
						}
					}
					//跨表计算
				}
				let oet = {}
				if(this.ruleForm.id){
					await this.$api.update(`fuwuyuyue`, this.ruleForm);
				}else{
					oet = await this.$api.add(`fuwuyuyue`, this.ruleForm);
				}
				if(this.isAuth('fuwuyuyue','支付')&&this.cross) {
					uni.showModal({
						title: '提交成功！',
						content: '是否跳转支付？',
						success: async function(res) {
							if (res.confirm) {
								that.$utils.jump(`./detail?id=${oet.data}&userid=` + uni.getStorageSync("appUserid"))
							}else {
								if(that.cross){
									uni.setStorageSync('crossCleanType',true);
								}
								that.$utils.msgBack('提交成功');
							}
						}
					})
				}else {
					if(this.cross){
						uni.setStorageSync('crossCleanType',true);
					}
					that.$utils.msgBack('提交成功');
				}
			},
			optionsChange(e) {
				this.index = e.target.value
			},
			bindDateChange(e) {
				this.date = e.target.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			toggleTab(str) {
				if(this.ro[str]){
					return false
				}
				this.$refs[str].show();
			},
			async chartClick1() {
				let params = {}
				let res = await this.$api.getPublic('fuwuyuyue/value/xiangmumingcheng/fuwufeiyong', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['xiangmumingcheng']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['xiangmumingcheng']
					})
				}
				this.opts1 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [15,10,0,15],
					enableScroll: false,
					legend: {
						show: false
					},
					xAxis: {
						disableGrid: true
					},
					yAxis: {
						gridType: "dash",
						dashLength: 2,
						showTitle: false
					},
					extra: {
						line: {
							type: "curve",
							width: 2,
							activeType: "hollow"
						}
					}
				}
				this.chartData1 = {
					categories: xAxis,
					series: [
						{
							data: yAxis,
							name: ''
						}
					]
				};
				this.chartType1 = true
			},
			async chartClick2() {
				let params = {}
				let res = await this.$api.getPublic('fuwuyuyue/group/fuwuleixing', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['fuwuleixing']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['fuwuleixing']
					})
				}
				this.opts2 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [15,10,0,15],
					enableScroll: false,
					legend: {
						show: false
					},
					xAxis: {
						disableGrid: true
					},
					yAxis: {
						gridType: "dash",
						dashLength: 2,
						showTitle: false
					},
					extra: {
						area: {
							type: "curve",
							opacity: 0.2,
							addLine: true,
							width: 2,
							gradient: true,
							activeType: "hollow"
						}
					}
				}
				this.chartData2 = {
					categories: xAxis,
					series: [
						{
							data: yAxis,
							name: ''
						}
					]
				};
				this.chartType2 = true
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
