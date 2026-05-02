  <template>
	<div class="main-content" :style='{"width":"100%","padding":"20px","fontSize":"14px","color":"#666","height":"auto"}'>
		<!-- 列表页 -->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 0px","flexWrap":"wrap","display":"flex"}' :inline="true" :model="searchForm">
				<el-row :style='{"border":"0px solid #e5e7eb","padding":"0","boxShadow":"none","margin":"0 10px 5px 0","borderRadius":"0","alignItems":"center","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto","position":"relative"}' >
				</el-row>
				<el-row :style='{"border":"0px solid #e5e7eb","padding":"0","boxShadow":"none","margin":"0 10px 5px 0","borderRadius":"0","alignItems":"center","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto","position":"relative"}'>
					<el-button :style='{"border":"0","cursor":"pointer","padding":"0 10px 0 8px","outline":"none","margin":"0 0 0 3px","color":"#fff","borderRadius":"4px","background":"#2dc7a3","width":"auto","fontSize":"inherit","height":"36px"}' type="success" @click="backClick()">返回</el-button>
				</el-row>

				<el-row class="actions" :style='{"border":"0px solid #e5e7eb","padding":"0","margin":"0 0 5px","borderRadius":"0","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto"}'>
					<el-button class="btn18" type="success" @click="refreshClick()">
						刷新
					</el-button>
					<el-button class="add" v-if="isAuth('discussfuwuxinxi','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia1" :style='{"margin":"0 2px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
						新增
					</el-button>
					<el-button class="del" v-if="isAuth('discussfuwuxinxi','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						批量删除
					</el-button>


				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"10px 0px 20px","boxShadow":"none","borderRadius":"0 0 4px 4px","background":"#fff"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#eee","borderRadius":"0","borderWidth":"1px 1px 0 1px","background":"#fff","width":"100%","fontSize":"inherit","borderStyle":"solid"}' 
					:border='false'
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="序号" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'
												prop="nickname"
						label="用户名">
						<template slot-scope="scope">
							{{scope.row.nickname}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="content" label="评论内容">
						<template slot-scope="scope">
							<span class="ql-snow ql-editor" v-html="scope.row.content"></span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="score" label="评分">
						<template slot-scope="scope">
							<el-rate
								v-model="scope.row.score"
								:style='{"lineHeight":"1","height":"20px"}'
								:max='5'
								:allow-half='false'
								:low-threshold='2'
								:high-threshold='4'
								:show-text='false'
								:texts='["极差", "失望", "一般", "满意", "惊喜"]'
								text-color='#1F2D3D'
								:colors='["#F7BA2A", "#F7BA2A", "#F7BA2A"]'
								void-color='#C6D1DE'
								disabled-void-color='#EFF2F7'
								:icon-classes='["el-icon-star-on", "el-icon-star-on","el-icon-star-on"]'
								void-icon-class='el-icon-star-off'
								disabled-void-icon-class='el-icon-star-on'
								:show-score='false'
								disabled
								>
							</el-rate>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="reply" label="回复内容">
						<template slot-scope="scope">
							<span class="ql-snow ql-editor" v-html="scope.row.reply"></span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="istop" label="是否置顶">
						<template slot-scope="scope">
							<el-switch
								v-model="scope.row.istop"
								:width="36"
								active-icon-class="el-icon-edit"
								inactive-icon-class="el-icon-share"
								active-text="开"
								inactive-text="关"
								:active-value="1"
								:inactive-value="0"
								active-color="#2dc7a3"
								inactive-color="#ccc"
								@change="(e)=>discussistopChange(e,scope.row)">
							</el-switch>
						</template>
					</el-table-column>
					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="edit" v-if=" isAuth('discussfuwuxinxi','修改') " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								编辑
							</el-button>

							<el-button class="view" v-if="isAuth('discussfuwuxinxi','查看评论')" type="success" @click="disscussListHandler(scope.row.id)">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								查看评论
							</el-button>


							<el-button class="btn8" type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-huifu18" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								回复
							</el-button>

							<el-button class="del" v-if="isAuth('fuwuxinxi','删除评论')" type="primary" @click="deleteHandler(scope.row.id ,scope.row.refid)">
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





	</div>
</template>

<script>
	import * as echarts from 'echarts'
	import chinaJson from "@/components/echarts/china.json";
	import axios from 'axios';
	import AddOrUpdate from "./add-or-update";
	import {
		Loading
	} from 'element-ui';
	export default {
		data() {
			return {
				indexQueryCondition: '',
				searchForm: {
					key: ""
				},
				form:{},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				addOrUpdateFlag:false,
				layouts: ["total","prev","pager","next","sizes","jumper"],
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
		},
		components: {
			AddOrUpdate,
		},
		methods: {
			refreshClick(){
				this.searchForm = {}
				this.search()
			},
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			init () {
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
					refid : this.$route.query.refid,
				}
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "discussfuwuxinxi/page",
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						for(let x in data.data.list) {
							if(data.data.list[x].content) {
								data.data.list[x].content = data.data.list[x].content.replace(/img src/gi,"img style=\"width:100%;\" src");
							}
							if(data.data.list[x].reply) {
								data.data.list[x].reply = data.data.list[x].reply.replace(/img src/gi,"img style=\"width:100%;\" src");
							}
						}
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
			// 查看评论
			disscussListHandler(id,type) {
				this.$router.push({path:'/discussdiscussfuwuxinxi',query:{refid:id}});
			},
			backClick(){
				history.back()
			},
			discussistopChange(e,row){
				this.$http({
					url:'discussfuwuxinxi/update',
					method:'post',
					data:row
				}).then(res=>{})
			},
			// 删除
			async deleteHandler(id ,refid ) {
				var ids = id? [Number(id)]: this.dataListSelections.map(item => {
					return Number(item.id);
				});
				await this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(async () => {
					await this.$http({
						url: "discussfuwuxinxi/delete",
						method: "post",
						data: ids
					}).then(async ({ data }) => {
						if (data && data.code === 0) {
							this.$http({
								url: 'fuwuxinxi/info/' + refid,
								method: "get",
							}).then(res=>{
								if(res.data&&res.data.code==0){
									res.data.data.discussnum = res.data.data.discussnum - Number(ids.length)
									this.$http({
										url: 'fuwuxinxi/update',
										method: "post",
										data:res.data.data
									}).then(res1=>{
										if(res1.data&&res1.data.code==0){
											this.$message({
												message: "操作成功",
												type: "success",
												duration: 1500,
												onClose: () => {
													this.search();
												}
											});
										}
									})
								}
							})
			
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

	.chartDialog ::v-deep .el-dialog {
		background: #fff;
	}
</style>
