  <template>
	<div class="main-content" :style='{"width":"100%","padding":"20px","fontSize":"14px","color":"#666","height":"auto"}'>
		<!-- 列表页 -->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 0px","flexWrap":"wrap","display":"flex"}' :inline="true" :model="searchForm">
				<el-row :style='{"border":"0px solid #e5e7eb","padding":"0","boxShadow":"none","margin":"0 10px 5px 0","borderRadius":"0","alignItems":"center","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto","position":"relative"}' >
					<div  :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">预约编号</label>
						<el-input v-model="searchForm.yuyuebianhao" placeholder="预约编号" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">项目编号</label>
						<el-input v-model="searchForm.xiangmubianhao" placeholder="项目编号" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">项目名称</label>
						<el-input v-model="searchForm.xiangmumingcheng" placeholder="项目名称" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}' class="select">
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">是否通过</label>
						<el-select clearable v-model="searchForm.sfsh" placeholder="是否通过">
							<el-option v-for="(item,index) in sfshOptions" v-bind:key="index" :label="item" :value="item"></el-option>
						</el-select>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing02" :style='{"margin":"0 0px","fontSize":"inherit","color":"#fff","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>

				<el-row class="actions" :style='{"border":"0px solid #e5e7eb","padding":"0","margin":"0 0 5px","borderRadius":"0","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto"}'>
					<el-button class="add" v-if="isAuth('fuwudingdan','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia1" :style='{"margin":"0 2px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
						新增
					</el-button>
					<el-button class="del" v-if="isAuth('fuwudingdan','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						批量删除
					</el-button>


					<el-button class="btn18" v-if="isAuth('fuwudingdan','审核')" :disabled="dataListSelections.length?false:true" type="success" @click="shBatchDialog()">
						<span class="icon iconfont icon-shenhe9" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						审核
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"10px 0px 20px","boxShadow":"none","borderRadius":"0 0 4px 4px","background":"#fff"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#eee","borderRadius":"0","borderWidth":"1px 1px 0 1px","background":"#fff","width":"100%","fontSize":"inherit","borderStyle":"solid"}' 
					:border='false'
					v-if="isAuth('fuwudingdan','查看')"
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="序号" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'
												prop="yuyuebianhao"
						label="预约编号">
						<template slot-scope="scope">
							{{scope.row.yuyuebianhao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="xiangmubianhao"
						label="项目编号">
						<template slot-scope="scope">
							{{scope.row.xiangmubianhao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="xiangmumingcheng"
						label="项目名称">
						<template slot-scope="scope">
							{{scope.row.xiangmumingcheng}}
						</template>
					</el-table-column>
					<el-table-column  :resizable='true' prop="xiangmutupian" width="200" label="项目图片">
						<template slot-scope="scope">
							<div v-if="scope.row.xiangmutupian">
								<img v-if="scope.row.xiangmutupian.substring(0,4)=='http'&&scope.row.xiangmutupian.split(',w').length>1" :src="scope.row.xiangmutupian" width="100" height="100" style="object-fit: cover" @click="imgPreView(scope.row.xiangmutupian)">
								<img v-else-if="scope.row.xiangmutupian.substring(0,4)=='http'" :src="scope.row.xiangmutupian.split(',')[0]" width="100" height="100" style="object-fit: cover" @click="imgPreView(scope.row.xiangmutupian.split(',')[0])">
								<img v-else :src="$base.url+scope.row.xiangmutupian.split(',')[0]" width="100" height="100" style="object-fit: cover" @click="imgPreView($base.url+scope.row.xiangmutupian.split(',')[0])">
							</div>
							<div v-else>
								<img :src="getDefaultImage(scope.row.id)" width="100" height="100" style="object-fit: cover">
							</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="fuwuleixing"
						label="服务类型">
						<template slot-scope="scope">
							{{scope.row.fuwuleixing}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="fuwufeiyong"
						label="服务费用">
						<template slot-scope="scope">
							{{scope.row.fuwufeiyong}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="fuwuneirong"
						label="服务内容">
						<template slot-scope="scope">
							{{scope.row.fuwuneirong}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="weizhi" label="位置">
						<template slot-scope="scope">
							{{ scope.row.weizhi?scope.row.weizhi.split(';')[2]:'' }}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yuyueriqi"
						label="预约日期">
						<template slot-scope="scope">
							{{scope.row.yuyueriqi}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="jiedanshijian"
						label="接单时间">
						<template slot-scope="scope">
							{{scope.row.jiedanshijian}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yuyueshiduan"
						label="预约时段">
						<template slot-scope="scope">
							{{scope.row.yuyueshiduan}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="fuwuzhuangtai"
						label="服务状态">
						<template slot-scope="scope">
							{{scope.row.fuwuzhuangtai}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yonghuzhanghao"
						label="用户账号">
						<template slot-scope="scope">
							{{scope.row.yonghuzhanghao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yonghuxingming"
						label="用户姓名">
						<template slot-scope="scope">
							{{scope.row.yonghuxingming}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="shoujihao"
						label="手机号">
						<template slot-scope="scope">
							{{scope.row.shoujihao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="fuwudizhi" label="服务地址">
						<template slot-scope="scope">
							{{ scope.row.fuwudizhi?scope.row.fuwudizhi.split(';')[2]:'' }}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yuangongzhanghao"
						label="员工账号">
						<template slot-scope="scope">
							{{scope.row.yuangongzhanghao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yuangongxingming"
						label="员工姓名">
						<template slot-scope="scope">
							{{scope.row.yuangongxingming}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yujidaodashijian"
						label="预计到达时间">
						<template slot-scope="scope">
							{{scope.row.yujidaodashijian}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="shhf" label="审核回复" show-overflow-tooltip>
						<template slot-scope="scope">
							<div style="white-space: nowrap;">{{scope.row.shhf}}</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="sfsh" label="审核状态">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.sfsh=='否'" type="danger">未通过</el-tag>
							<el-tag v-if="scope.row.sfsh=='待审核'" type="warning">待审核</el-tag>
							<el-tag v-if="scope.row.sfsh=='是'" type="success">通过</el-tag>
						</template>
					</el-table-column>
					
					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="view" v-if=" isAuth('fuwudingdan','查看')" type="success" @click="addOrUpdateHandler(scope.row.id,'info')">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								查看
							</el-button>
							<el-button class="btn8" v-if="isAuth('fuwudingdan','服务反馈')" @click="fuwujiluCrossAddOrUpdateHandler(scope.row,'cross','是','','fuwuzhuangtai','已服务','已服务,待服务'.split(',')[0])" type="success">
								<span class="icon iconfont icon-xihuan" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								服务反馈
							</el-button>
							<el-button class="edit" v-if=" isAuth('fuwudingdan','修改')  && scope.row.sfsh=='待审核' " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								编辑
							</el-button>

							<el-button class="btn8" v-if="isAuth('fuwudingdan','私聊')" type="success" @click="chatClick(scope.row)">
								<span class="icon iconfont icon-zhangjie7" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								联系TA							</el-button>



							<el-button v-if="isAuth('fuwudingdan','地图')" class="btn8" type="success" @click="showClick(scope.row)">
								<span class="icon iconfont icon-queren12" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								地图
							</el-button>
							<el-button class="del" v-if="isAuth('fuwudingdan','删除')" type="primary" @click="deleteHandler(scope.row.id)">
								<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				background
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:layout="layouts.join()"
				:total="totalPage"
				prev-text="< "
				next-text="> "
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","display":"flex","width":"100%","fontWeight":"500","justifyContent":"flex-end"}'
			></el-pagination>
		</template>
		<!-- 添加/修改页面  将父组件的search方法传递给子组件-->
		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>

		<fuwujilu-cross-add-or-update v-if="fuwujiluCrossAddOrUpdateFlag" :parent="this" ref="fuwujiluCrossaddOrUpdate"></fuwujilu-cross-add-or-update>

		
		<el-dialog :title="this.batchIds.length>1?'批量审核':'审核'" :visible.sync="sfshBatchVisiable" width="50%">
			<el-form ref="shBatchForm" :model="shBatchForm" :rules="shRules" label-width="80px">
				<el-form-item label="审核状态" prop="sfsh">
					<el-select v-model="shBatchForm.sfsh" placeholder="审核状态">
						<el-option label="通过" value="是"></el-option>
						<el-option label="不通过" value="否"></el-option>
						<el-option label="待审核" value="待审核"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="内容" prop="shhf">
					<el-input type="textarea" :rows="8" v-model="shBatchForm.shhf"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="sfshBatchVisiable=false">取 消</el-button>
				<el-button type="primary" @click="shBatchHandler">确 定</el-button>
			</span>
		</el-dialog>



		<el-dialog :visible.sync="chatVisible" @close="clearChat" :title="nowname" :append-to-body="true">
			<div class="chat-content" id="chat-content">
				<div v-bind:key="item.id" v-for="item in chatList">
					<div v-if="item.addtime" class="addtime">{{timeFormat(item.addtime)}}</div>
					<div v-if="item.uid==myid" class="right-content">
						<el-alert v-if="item.format==1" class="text-content" :title="item.content" :closable="false"
							type="warning"></el-alert>
						<el-image v-else-if="item.format==2" fit="cover" :src="item.content?$base.url + item.content:''"
							style="width: 100px;height: 100px;"
							:preview-src-list="[item.content?$base.url + item.content:'']"></el-image>
						<video v-else-if="item.format==3" :src="$base.url + item.content" style="width: 280px;" controls></video>
						<el-button v-else-if="item.format==4" type="primary" size="mini" @click="chatDownload(item.content)">文件预览</el-button>
						<audio v-else-if="item.format==5" :src="$base.url + item.content" controls></audio>
						<img :src="avatar?$base.url + avatar:require('@/assets/img/avator.png')" alt=""
							style="width: 30px;border-radius: 50%;height: 30px;margin: 0 0 0 10px;" />
					</div>
					<div v-else class="left-content">
						<img :src="nowfpic?$base.url + nowfpic:require('@/assets/img/avator.png')" alt=""
							style="width: 30px;border-radius: 50%;height: 30px;margin: 0 10px 0 0;" />
						<el-alert v-if="item.format==1" class="text-content" :title="item.content" :closable="false"
							type="success"></el-alert>
						<el-image v-else-if="item.format==2" fit="cover" :src="item.content?$base.url + item.content:''"
							style="width: 100px;height: 100px;"
							:preview-src-list="[item.content?$base.url + item.content:'']"></el-image>
						<video v-else-if="item.format==3" :src="$base.url + item.content" style="width: 280px;" controls></video>
						<el-button v-else-if="item.format==4" type="primary" size="mini" @click="chatDownload(item.content)">文件预览</el-button>
						<audio v-else-if="item.format==5" :src="$base.url + item.content" controls></audio>
					</div>
					<div class="clear-float"></div>
				</div>
			</div>
			<div slot="footer" class="dialog-footer">
				<div style="display: flex;align-items: center">
					<el-input @keydown.enter.native="addChat(null)" v-model="chatForm.content" placeholder="请输入内容"
						style="width: calc(100% - 180px);float: left;">
					</el-input>
					<el-button :disabled="chatForm.content?false:true" type="primary" @click="addChat(null)">发送</el-button>
					<el-button
						type="warning" 
						:icon="isRecording ? 'el-icon-video-pause' : 'el-icon-microphone'"
						@click="toggleRecord"
						:class="{'voice-record-btn': true, 'voice-recording': isRecording}"
						style="margin: 0 0 0 6px;">
						{{ isRecording ? `录音中 ${recordDuration}秒 (点击停止)` : '点击录音' }}
					</el-button>
					<el-upload style="display: inline-block;margin: 0 0 0 6px;" class="upload-demo" :action="uploadUrl" accept="image/*"
						:on-success="uploadSuccess" :show-file-list="false">
						<el-button type="success">上传图片</el-button>
					</el-upload>
					<el-upload class="upload-demo" :action="uploadUrl" :on-success="uploadSuccess2" accept="video/*"
						:show-file-list="false">
						<el-button type="success">上传视频</el-button>
					</el-upload>
					<el-upload class="upload-demo" :action="uploadUrl" :on-success="uploadSuccess3"
						:show-file-list="false">
						<el-button type="success">上传文件</el-button>
					</el-upload>
				</div>
			</div>
		</el-dialog>
		<el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
		<el-dialog title="地图" :visible.sync="showVisible" width="50%" @close="mapClose">
			<tMap ref="tmap"></tMap>
		</el-dialog>
	</div>
</template>

<script>
	import * as echarts from 'echarts'
	import chinaJson from "@/components/echarts/china.json";
	import axios from 'axios';
	import AddOrUpdate from "./add-or-update";
	import fuwujiluCrossAddOrUpdate from "../fuwujilu/add-or-update";
	import timeMethod from '@/components/common/timeMethod'
	import {
		WebsocketMixin
	} from '@/mixins/WebsocketMixin'
	import tMap from '@/components/common/tmap'
	import {
		Loading
	} from 'element-ui';
	export default {
		mixins: [WebsocketMixin],
		data() {
			return {
				indexQueryCondition: '',
				searchForm: {
					key: ""
				},
				form:{},
				sfshOptions: [],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				sfshVisiable: false,
				shForm: {},
				sfshBatchVisiable: false,
				shBatchForm: {
					sfsh:'',
					shhf:''
				},
				shRules: {
					sfsh:[{ required: true, message: '审核状态不能为空', trigger: 'blur' },],
				},
				batchIds:[],
				shList: [],
				addOrUpdateFlag:false,
				fuwujiluCrossAddOrUpdateFlag: false,
				layouts: ["total","prev","pager","next","sizes","jumper"],
				chatVisible: false,
				nowfid: 0,
				nowfpic: '',
				nowname: '',
				nowtableName: 'fuwudingdan',
				chatList: [],
				chatForm: {
					content: ''
				},
				uploadUrl: this.$base.url + 'file/upload',
				// 语音聊天相关
				isRecording: false,
				mediaRecorder: null,
				audioStream: null,
				audioChunks: [],
				recordDuration: 0,
				recordTimer: null,
				previewImg: '',
				previewVisible: false,
				showVisible: false,
			};
		},
		created() {
			if(this.statType) {
				return false
			}
			this.init();
			this.getDataList();
		},
		mounted() {
		},
		watch: {
		},
		filters: {
			htmlfilter: function (val) {
				return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
			}
		},
		computed: {
			tablename(){
				return this.$storage.get('sessionTable')
			},
			role(){
				return this.$storage.get('role')
			},
			avatar() {
				return this.$storage.get('headportrait') ? this.$storage.get('headportrait') : ''
			},
			myid() {
				return this.$storage.get('userid') ? this.$storage.get('userid') : ''
			},
		},
		components: {
			AddOrUpdate,
			fuwujiluCrossAddOrUpdate,
			tMap,
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
			mapClose(){
				this.$refs.tmap.mapClose()
			},
			showClick(row){
				let longitude1,longitude2,latitude1,latitude2
				let waypoints = []
				if(row.weizhi&&row.weizhi.split(';').length>1) {
					longitude1 = row.weizhi.split(';')[0]
					latitude1 = row.weizhi.split(';')[1]
				}
				if(row.fuwudizhi&&row.fuwudizhi.split(';').length>1) {
					longitude2 = row.fuwudizhi.split(';')[0]
					latitude2 = row.fuwudizhi.split(';')[1]
				}
				this.showVisible = true
				this.$nextTick(()=>{
					this.$refs.tmap.initAMap(longitude1,longitude2,latitude1,latitude2,waypoints)
				})
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
				
			},
			getDefaultImage(id){
				const random = (id * 9973) % 22 + 1
				return `/systemPhotos/default/default${random}.jpg`
			},
			chatClick(row) {
				let user = JSON.parse(this.$storage.getObj('userForm'))
				let chatUser = ''
				let chatUserName = ''
				let chatUserName2 = ''
				if(!chatUser||this.tablename == chatUser) {
					chatUserName2 = ''
					chatUser = 'yonghu'
					chatUserName = 'yonghuzhanghao'
				}
				if(!chatUser||this.tablename == chatUser) {
					chatUserName2 = ''
					chatUser = 'fuwurenyuan'
					chatUserName = 'yuangongzhanghao'
				}
				if(this.tablename == chatUser) {
					if(row[chatUserName] == user[chatUserName]){
						this.$message.error('不能给自己发信息')
						return false
					}
				}
				this.nowtableName = chatUser
				let params = {}
				params[chatUserName2?chatUserName2:chatUserName] = row[chatUserName]
				this.$http.get(`${chatUser}/query`, {
					params: params
				}).then(res => {
					if (res.data && res.data.code == 0) {
						this.nowfid = res.data.data.id
						this.initWebSocket(this.nowfid)
						this.nowname = res.data.data[chatUserName2?chatUserName2:chatUserName]
						if (res.data.data.touxiang) {
							this.nowfpic = res.data.data.touxiang.split(',')[0]
						} else if (res.data.data.headportrait) {
							this.nowfpic = res.data.data.headportrait.split(',')[0]
						}
						this.getChatList()
						this.chatVisible = true
					}
				})
			},
			websocketOnopen: function() {
				
			},
			websocketOnmessage:function(e) {
				this.getChatList()
			},
			getChatList() {
				this.$http.get('chatmessage/mlist', {
					params: {
						page: 1,
						limit: 1000,
						uid: this.myid,
						fid: this.nowfid
					}
				}).then(res => {
					if (res.data && res.data.code == 0) {
						this.chatList = this.formatMessages(res.data.data.list)
						let div = document.getElementsByClassName('chat-content')[0]
						setTimeout(() => {
							if (div)
								div.scrollTop = div.scrollHeight
						}, 0)
					}
				})
			},
			formatMessages(messages) {
				let lastTime = null;
				messages.forEach((message, index) => {
					const currentTime = new Date(message.addtime).getTime();
					if (lastTime !== null) {
						const timeDiff = (currentTime - lastTime) / 1000 / 60; // 转换为分钟
						if (timeDiff < 3) {
							message.addtime = ''; // 如果小于3分钟，不显示时间
						}
					}
					lastTime = currentTime;
				});
				return messages;
			},
			timeFormat(time) {
				const Time = timeMethod.getTime(time).split("T");
				//当前消息日期属于周
				const week = timeMethod.getDateToWeek(time);
				//当前日期0时
				const nti = timeMethod.setTimeZero(timeMethod.getNowTime());
				//消息日期当天0时
				const mnti = timeMethod.setTimeZero(timeMethod.getTime(time));
				//计算日期差值
				const diffDate = timeMethod.calculateTime(nti, mnti);
				//本周一日期0时 （后面+1是去除当天时间）
				const fwnti = timeMethod.setTimeZero(timeMethod.countDateStr(-timeMethod.getDateToWeek(timeMethod
					.getNowTime()).weekID + 1));
				//计算周日期差值
				const diffWeek = timeMethod.calculateTime(mnti, fwnti);

				if (diffDate === 0) { //消息发送日期减去当天日期如果等于0则是当天时间
					return Time[1].slice(0, 5);
				} else if (diffDate < 172800000) { //当前日期减去消息发送日期小于2天（172800000ms）则是昨天-  一天最大差值前天凌晨00:00:00到今天晚上23:59:59
					return "昨天 " + Time[1].slice(0, 5);
				} else if (diffWeek >= 0) { //消息日期减去本周一日期大于0则是本周
					return week.weekName;
				} else { //其他时间则是日期
					return Time[0].slice(5, 10);
				}
			},
			clearChat() {
				this.websocketOnclose();
				this.chatList = []
			},
			// 切换录音状态（点击开始/停止）
			toggleRecord() {
				if (this.isRecording) {
					// 如果正在录音，则停止
					this.stopRecord()
				} else {
					// 如果未录音，则开始
					this.startRecord()
				}
			},
			// 开始录音
			async startRecord() {
				if (this.isRecording) return
				
				try {
					// 请求麦克风权限
					this.audioStream = await navigator.mediaDevices.getUserMedia({ audio: true })
					
					// 检查浏览器是否支持 MediaRecorder
					if (!window.MediaRecorder) {
						this.$message.error('您的浏览器不支持录音功能')
						return
					}
					
					// 获取支持的 MIME 类型
					let mimeType = 'audio/webm'
					if (!MediaRecorder.isTypeSupported('audio/webm')) {
						if (MediaRecorder.isTypeSupported('audio/mp4')) {
							mimeType = 'audio/mp4'
						} else if (MediaRecorder.isTypeSupported('audio/ogg')) {
							mimeType = 'audio/ogg'
						} else {
							mimeType = '' // 使用浏览器默认格式
						}
					}
					
					// 初始化录音器
					const options = mimeType ? { mimeType: mimeType } : {}
					this.mediaRecorder = new MediaRecorder(this.audioStream, options)
					this.audioChunks = []
					
					// 监听数据可用事件（每100ms收集一次数据）
					this.mediaRecorder.ondataavailable = (event) => {
						if (event.data && event.data.size > 0) {
							this.audioChunks.push(event.data)
						}
					}
					
					// 监听录音停止事件
					this.mediaRecorder.onstop = () => {
						// 停止所有音频轨道
						if (this.audioStream) {
							this.audioStream.getTracks().forEach(track => track.stop())
							this.audioStream = null
						}
					}
					
					// 监听错误事件
					this.mediaRecorder.onerror = (event) => {
						console.error('录音错误:', event.error)
						this.$message.error('录音过程中发生错误')
						this.stopRecord()
					}
					
					// 开始录音，每100ms收集一次数据
					this.mediaRecorder.start(100)
					this.isRecording = true
					this.recordDuration = 0
					
					// 开始计时
					this.recordTimer = setInterval(() => {
						this.recordDuration++
						// 最长录音60秒
						if (this.recordDuration >= 60) {
							this.stopRecord()
							this.$message.info('录音已达到最大时长，已自动停止')
						}
					}, 1000)
					
				} catch (error) {
					console.error('无法访问麦克风:', error)
					this.isRecording = false
					if (error.name === 'NotAllowedError' || error.name === 'PermissionDeniedError') {
						this.$message.error('麦克风权限被拒绝，请在浏览器设置中允许访问麦克风')
					} else if (error.name === 'NotFoundError' || error.name === 'DevicesNotFoundError') {
						this.$message.error('未检测到麦克风设备')
					} else {
						this.$message.error('无法访问麦克风，请检查权限设置')
					}
				}
			},
			// 停止录音
			stopRecord() {
				if (!this.isRecording) return
				
				// 清除计时器
				if (this.recordTimer) {
					clearInterval(this.recordTimer)
					this.recordTimer = null
				}
				
				// 停止录音
				if (this.mediaRecorder && this.mediaRecorder.state !== 'inactive') {
					try {
						this.mediaRecorder.stop()
					} catch (e) {
						console.error('停止录音失败:', e)
					}
				}
				
				this.isRecording = false
				
				// 如果录音时长太短，不发送
				if (this.recordDuration < 1) {
					this.$message.warning('录音时间太短，请至少录音1秒')
					this.recordDuration = 0
					this.audioChunks = []
					return
				}
				
				// 等待录音数据收集完成
				setTimeout(() => {
					this.uploadAudio()
				}, 200)
			},
			// 上传录音
			uploadAudio() {
				if (this.audioChunks.length === 0) {
					this.$message.error('录音数据为空')
					return
				}
				
				// 确定文件扩展名
				let fileExtension = 'webm'
				let mimeType = 'audio/webm'
				if (this.mediaRecorder && this.mediaRecorder.mimeType) {
					mimeType = this.mediaRecorder.mimeType
					if (mimeType.includes('mp4')) {
						fileExtension = 'mp4'
					} else if (mimeType.includes('ogg')) {
						fileExtension = 'ogg'
					} else if (mimeType.includes('wav')) {
						fileExtension = 'wav'
					}
				}
				
				// 合并音频数据
				const audioBlob = new Blob(this.audioChunks, { type: mimeType })
				
				// 检查文件大小（限制为10MB）
				if (audioBlob.size > 10 * 1024 * 1024) {
					this.$message.error('录音文件过大，请缩短录音时间')
					this.audioChunks = []
					this.recordDuration = 0
					return
				}
				
				// 创建FormData上传
				const formData = new FormData()
				formData.append('file', audioBlob, `voice_${Date.now()}.${fileExtension}`)
				
				// 显示上传提示
				const loading = this.$loading({
					lock: true,
					text: '正在上传语音...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				})
				
				// 上传文件
				this.$http.post(this.uploadUrl, formData, {
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				}).then(res => {
					loading.close()
					if (res.data && res.data.code == 0) {
						// 上传成功，发送语音消息
						this.addChat('upload/' + res.data.file, 5)
						this.$message.success('语音发送成功')
					} else {
						this.$message.error(res.data.msg || '上传失败')
					}
				}).catch(error => {
					loading.close()
					console.error('上传失败:', error)
					this.$message.error('语音上传失败，请重试')
				}).finally(() => {
					// 重置状态
					this.audioChunks = []
					this.recordDuration = 0
					this.mediaRecorder = null
				})
			},
			uploadSuccess(res) {
				if (res.code == 0) {
					this.addChat('upload/' + res.file,2);
				}
			},
			uploadSuccess2(res) {
				if (res.code == 0) {
					this.addChat('upload/' + res.file,3);
				}
			},
			uploadSuccess3(res) {
				if (res.code == 0) {
					this.addChat('upload/' + res.file,4);
				}
			},
			addChat(ask=null,type=1) {
				this.$http.get('friend/page', {
					params: {
						uid: Number(this.myid),
						fid: Number(this.nowfid),
					}
				}).then(obj => {
					if (obj.data && obj.data.code == 0) {
						if (!obj.data.data.list.length) {
							this.$http.post('friend/add', {
								uid: Number(this.myid),
								fid: Number(this.nowfid),
								name: this.nowname,
								picture: this.nowfpic,
								type: 2,
								tablename: this.nowtableName,
							}).then(res => {
								this.$http.post('friend/add', {
									uid: this.nowfid,
									fid: this.myid,
									type: 2,
									tablename: this.tablename,
									name: this.$storage.get('adminName'),
									picture: this.avatar,
								}).then(res1 => {
			
								})
							})
						}
						this.$http.post('chatmessage/add', {
							uid: Number(this.myid),
							fid: Number(this.nowfid),
							content: ask?ask:this.chatForm.content,
							format: type
						}).then(res2 => {
							this.websocketSend(ask?ask:this.chatForm.content)
							this.chatForm = {
								content: ''
							}
							this.getChatList()
						})
					}
				})
			},
			// 预览
			chatDownload(file){
				if(!file){
					return false
				}
				window.open((location.href.split(this.$base.name).length>1 ? location.href.split(this.$base.name)[0] + this.$base.name + '/' + file :this.$base.url + file))
			},
			fuwujiluCrossAddOrUpdateHandler(row,type,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue){
				if(crossOptAudit=='是'&&row.sfsh!='是') {
					this.$message({
						message: "请审核通过后再操作",
						type: "warning",
						duration: 1500,
						onClose: () => {
						}
					});
					return
				}
				this.showFlag = false;
				this.addOrUpdateFlag = false;
				this.fuwujiluCrossAddOrUpdateFlag = true;
				this.$storage.set('crossObj',row);
				this.$storage.set('crossTable','fuwudingdan');
				this.$storage.set('statusColumnName',statusColumnName);
				this.$storage.set('statusColumnValue',statusColumnValue);
				this.$storage.set('tips',tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = this.$storage.getObj('crossObj');
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$message({
								message: tips,
								type: "warning",
								duration: 1500,
								onClose: () => {
									this.getDataList();
								}
							});
							this.showFlag = true;
							this.fuwujiluCrossAddOrUpdateFlag = false;
							return;
						}
					}
				}
				this.$nextTick(() => {
					this.$refs.fuwujiluCrossaddOrUpdate.init(row.id,type);
				});
			},
			init () {
				this.sfshOptions = "是,否,待审核".split(',');
				let urlfuwuzhuangtai = 'fuwudingdan/remind/fuwuzhuangtai/3';
				urlfuwuzhuangtai+=`?remindstart=待服务`,

				this.$http({
					url: urlfuwuzhuangtai,
					method: "get",
				}).then(({ data }) => {
					if (data && data.code == 0 && data.count>0 ) {
						const h = this.$createElement;
						var name = data.data
						this.fuwuzhuangtaiRemind = data.data
						var arr = []
						for(let x=0;x<name.length;x++) {
							arr.push(h('span',{style: 'color: teal;font-weight: bold;cursor: pointer;text-decoration: underline;', on: { click: () => this.changeClick(name[x]) }}, `${name[x]}${(x!=name.length - 1)?'，':''}`))
						}
						this.$msgbox({
							message: h('p', null, [
								h('div', { style: 'text-align: center;font-weight: bold;font-size: 20px;padding: 20px 0' }, `${data.count}条服务状态预警`),
								h('p', null, [
									h('div', { style: 'text-align: center;font-weight: bold;padding: 0 0 10px;font-size: 16px' }, `待服务订单`),
									h('div', { style: 'text-align: center' }, [
										h('span', null, `包含：`),
										arr
									])
								])
							]),
							showCancelButton: false,
						})
					}
				});
			},
			search() {
				this.pageIndex = 1;
				this.getDataList();
			},

			// 获取数据列表
			getDataList() {
				this.dataListLoading = true;
				let params = {
					page: this.pageIndex,
					limit: this.pageSize,
					sort: 'id',
					order: 'desc',
				}
				if(this.searchForm.yuyuebianhao!='' && this.searchForm.yuyuebianhao!=undefined){
					params['yuyuebianhao'] = '%' + this.searchForm.yuyuebianhao + '%'
				}
				if(this.searchForm.xiangmubianhao!='' && this.searchForm.xiangmubianhao!=undefined){
					params['xiangmubianhao'] = '%' + this.searchForm.xiangmubianhao + '%'
				}
				if(this.searchForm.xiangmumingcheng!='' && this.searchForm.xiangmumingcheng!=undefined){
					params['xiangmumingcheng'] = '%' + this.searchForm.xiangmumingcheng + '%'
				}
				if(this.searchForm.sfsh!='' && this.searchForm.sfsh!=undefined){
					params['sfsh'] = this.searchForm.sfsh
				}
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "fuwudingdan/page",
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.dataList = data.data.list;
						this.totalPage = data.data.total;
					} else {
						this.dataList = [];
						this.totalPage = 0;
					}
					this.dataListLoading = false;
				});
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.getDataList();
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val;
				this.getDataList();
			},
			// 多选
			selectionChangeHandler(val) {
				this.dataListSelections = val;
			},
			// 添加/修改
			addOrUpdateHandler(id,type) {
				this.showFlag = false;
				this.addOrUpdateFlag = true;
				this.crossAddOrUpdateFlag = false;
				if(type!='info'&&type!='msg'){
					type = 'else';
				}
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,type );
				});
			},
			//批量审核窗口
			shBatchDialog(){
				for(let x in this.dataListSelections){
					if(this.dataListSelections[x].sfsh&&this.dataListSelections[x].sfsh!='待审核'){
						this.$message.error('存在已审核数据，不能继续操作');
						this.batchIds = []
						return false
					}
					this.batchIds.push(this.dataListSelections[x].id)
				}
				this.shList = this.dataListSelections
				this.sfshBatchVisiable = true
			},
			//批量审核
			shBatchHandler(){
				this.$refs["shBatchForm"].validate(valid => {
					if(valid){
						this.$confirm(`是否${this.batchIds.length>1?'一键审核':'审核'}选中数据?`, "提示", {
							confirmButtonText: "确定",
							cancelButtonText: "取消",
							type: "warning"
						}).then(async() => {
							let changeType = true
							let errMsg = ''
							if(this.shBatchForm.sfsh=='是') {
								for(let x in this.shList){
									await this.$http.post('updateColumn/fuwuyuyue/1',{
										csuUpdateColumn: 'dingdanzhuangtai',
										csuUpdateColumnValue: '已派单',
										csuConditionColumn: 'yuyuebianhao',
										csuConditionColumnValue: this.shList[x].yuyuebianhao
									}).then(rs=>{
									})
								}
							}
							for(let x in this.shList){
								if(this.shBatchForm.sfsh=='否') {
									await this.$http.post('updateColumn/fuwuyuyue/1',{
										csuUpdateColumn: 'dingdanzhuangtai',
										csuUpdateColumnValue: '预约中',
										csuConditionColumn: 'yuyuebianhao',
										csuConditionColumnValue: this.shList[x].yuyuebianhao
									}).then(rs=>{
									})
								}
							}
							if(this.shBatchForm.sfsh=='是') {
								for(let x in this.shList){
									await this.$http.post('updateColumn/fuwurenyuan/1',{
										csuUpdateColumn: 'fuwuzhuangtai',
										csuUpdateColumnValue: '工作中',
										csuConditionColumn: 'yuangongzhanghao',
										csuConditionColumnValue: this.shList[x].yuangongzhanghao
									}).then(rs=>{
									})
								}
							}
							this.$http({
								url: "fuwudingdan/shBatch?sfsh="+this.shBatchForm.sfsh+"&shhf="+this.shBatchForm.shhf,
								method: "post",
								data: this.batchIds
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									this.$message({
										message: "操作成功",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.getDataList();
											this.sfshBatchVisiable = false
											this.batchIds = []
										}
									});
								} else {
									this.$message.error(data.msg);
								}
							});
						});
					}
				})
			},
			// 删除
			async deleteHandler(id ) {
				var ids = id? [Number(id)]: this.dataListSelections.map(item => {
					return Number(item.id);
				});
				await this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(async () => {
					await this.$http({
						url: "fuwudingdan/delete",
						method: "post",
						data: ids
					}).then(async ({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "操作成功",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
			
						} else {
							this.$message.error(data.msg);
						}
					});
				});
			},


		}

	};
</script>
<style lang="scss" scoped>
	
	.center-form-pv {
		.el-date-editor.el-input {
			width: auto;
		}
	}
	
	.el-input {
		width: auto;
	}
	
	// form
	.center-form-pv .el-input {
		width: auto;
	}
	.center-form-pv .el-input ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	.center-form-pv .el-select {
		width: auto;
	}
	.center-form-pv .el-select ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	.center-form-pv .el-date-editor {
		width: auto;
	}
	
	.center-form-pv .el-date-editor ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px 0 25px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	
	.center-form-pv .search {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px 0 8px;
		outline: none;
		margin: 0 0 0 3px;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	
	.center-form-pv .search:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .add {
		border: 0px solid #6c63ff80;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #31c72c;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .add:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .del {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #bd3124;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .del:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .statis {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #27ba65;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .statis:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .btn18 {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .btn18:hover {
		opacity: 0.8;
	}
	
	// table
	.el-table ::v-deep .el-table__header-wrapper thead {
		color: #999;
		font-weight: 500;
		width: 100%;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr {
		background: #f2f2f2;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th {
		padding: 3px 0;
		background: none;
		border-color: #ddd;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: center;
	}

	.el-table ::v-deep .el-table__header-wrapper thead tr th .cell {
		padding: 0 0 0 5px;
		word-wrap: normal;
		color: #333;
		white-space: normal;
		font-weight: bold;
		display: flex;
		vertical-align: middle;
		font-size: 14px;
		line-height: 24px;
		text-overflow: ellipsis;
		word-break: break-all;
		width: 100%;
		justify-content: flex-start;
		align-items: center;
		position: relative;
		min-width: 110px;
	}

	.el-table ::v-deep .el-table__body-wrapper {
		position: relative;
	}
	.el-table ::v-deep .el-table__body-wrapper tbody {
		width: 100%;
	}

	.el-table ::v-deep .el-table__body-wrapper tbody tr {
		background: #fff;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
		
	.el-table ::v-deep .el-table__body-wrapper tbody tr:hover td {
		padding: 4px 0;
		color: #2dc7a3;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}

	.el-table ::v-deep .el-table__body-wrapper tbody tr td .cell {
		padding: 0 0 0 5px;
		overflow: hidden;
		word-break: break-all;
		white-space: normal;
		font-size: inherit;
		line-height: 24px;
		text-overflow: ellipsis;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: 3;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .view:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add:hover {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2d9dc6;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: -1;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #c72d46;
		width: auto;
		font-size: inherit;
		height: 32px;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .del:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8 {
		border: 0px solid #165dff50;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 5px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2dc75f;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: 5;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8:hover {
		opacity: 0.8;
	}
	
	// pagination
	.main-content .el-pagination ::v-deep .el-pagination__total {
		margin: 0 auto 0 0;
		color: #666;
		font-weight: 400;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-prev {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-next {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-prev:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-next:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}

	.main-content .el-pagination ::v-deep .el-pager {
		padding: 0;
		margin: 0;
		display: inline-block;
		vertical-align: top;
	}

	.main-content .el-pagination ::v-deep .el-pager .number {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #666;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #fff;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pager .number:hover {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #2dc7a3;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pager .number.active {
		cursor: default;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #2dc7a3;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes {
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input {
		margin: 0 5px;
		width: 100px;
		position: relative;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 25px 0 8px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input span.el-input__suffix {
		top: 0;
		position: absolute;
		right: 0;
		height: 100%;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
		cursor: pointer;
		color: #C0C4CC;
		width: 25px;
		font-size: 15px;
		line-height: 28px;
		text-align: center;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump {
		margin: 0 0 0 24px;
		color: #606266;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input {
		border-radius: 3px;
		padding: 0 2px;
		margin: 0 2px;
		display: inline-block;
		width: 50px;
		font-size: 15px;
		line-height: 18px;
		position: relative;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 3px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	// list one
	.one .list1-view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-view:hover {
		opacity: 0.8;
	}
	
	.one .list1-edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f60;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-edit:hover {
		opacity: 0.8;
	}
	
	.one .list1-del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f00;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-del:hover {
		opacity: 0.8;
	}
	
	.one .list1-btn8 {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-btn8:hover {
		opacity: 0.8;
	}
	
	.main-content .el-table .el-switch {
		display: inline-flex;
		vertical-align: middle;
		line-height: 30px;
		position: relative;
		align-items: center;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--left {
		cursor: pointer;
		margin: 0 10px 0 0;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--right {
		cursor: pointer;
		margin: 0 0 0 10px;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core {
		border: 1px solid #ff8b00;
		cursor: pointer;
		border-radius: 15px;
		margin: 0;
		outline: 0;
		background: #ff8b00;
		display: inline-block;
		width: 36px;
		box-sizing: border-box;
		transition: border-color .3s,background-color .3s;
		height: 18px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core::after {
		border-radius: 100%;
		top: 1px;
		left: 2px;
		background: #FFF;
		width: 14px;
		position: absolute;
		transition: all .3s;
		height: 14px;
	}
	.main-content .el-table .el-switch.is-checked ::v-deep .el-switch__core::after {
		margin: 0 0 0 -16px;
		left: 100%;
	}
	
	.main-content .el-table .el-rate ::v-deep .el-rate__item {
		cursor: pointer;
		display: inline-block;
		vertical-align: middle;
		font-size: 0;
		position: relative;
	}
	.main-content .el-table .el-rate ::v-deep .el-rate__item .el-rate__icon {
		margin: 0 3px;
		display: inline-block;
		font-size: 18px;
		position: relative;
		transition: .3s;
	}

	.section-content {
		cursor: pointer;
		padding: 20px;
		box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.3);
		margin: 0 0 20px;
		color: #333;
		background: #fff;
		display: flex;
		width: 100%;
		border-color: #efefef;
		border-width: 0;
		align-items: center;
		border-style: solid;
		position: relative;
	}
	
	.section-content:hover {
		color: #fff;
		background: #DF847F10;
	}
	.chat-content {
		padding-bottom: 20px;
		width: 100%;
		margin-bottom: 10px;
		max-height: 300px;
		height: 300px;
		overflow-y: scroll;
		border: 1px solid #eeeeee;
		background: #fff;
	
		.addtime {
			width: 100%;
			text-align: center;
			font-size: 12px;
		}
	
		.left-content {
			float: left;
			margin-bottom: 10px;
			padding: 10px;
			max-width: 80%;
			display: flex;
			align-items: center;
		}
	
		.right-content {
			float: right;
			margin-bottom: 10px;
			padding: 10px;
			max-width: 80%;
			display: flex;
			align-items: center;
		}
	}
	
	.clear-float {
		clear: both;
	}
	.noList {
		color: #9e9e9e;
		width: 100%;
		text-align: center;
		padding: 60px 0;
	}
	.chartDialog ::v-deep .el-dialog {
		background: #fff;
	}
</style>
