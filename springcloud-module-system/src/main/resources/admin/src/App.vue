<template>
	<div id="app" class="">
		<router-view></router-view>
		<el-dialog title="系统配置" :visible.sync="configVisible" width="30%">
			<el-table :data="configList" border @row-click="configRowClick">
				<el-table-column prop="name" label="名称">
					<template slot-scope="scope">
						{{configNameChange(scope.row.name)}}
					</template>
				</el-table-column>
			</el-table>
		</el-dialog>
		<el-dialog :title="configNameChange(configTitle)" :visible.sync="configVisible2" width="30%">
			<el-form label-width="120px">
				<el-form-item v-for="(item,index) in configForm" :label="index" :prop="index">
					<el-input v-model="configForm[index]" :placeholder="index"></el-input>
				</el-form-item>
				<el-form-item class="btn">
					<el-button type="primary" size="mini" @click="configSave">
						保存
					</el-button>
					<el-button size="mini" @click="configVisible2=false">
						取消
					</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: 'app',
		data() {
			return{
				configForm: {},
				configValueForm: {},
				configVisible: false,
				configVisible2: false,
				configList: [],
				configTitle: ''
			}
		},
		created() {
			
		},
		mounted() {
			const handler = (e) => {
				if (e.ctrlKey && e.shiftKey && e.code === 'KeyY') {
					if (!this.isAdminLoggedIn()) {
						return
					}
					this.$http.get('config/page', {
						params: {
							type: 2
						}
					}).then(rs => {
						if (rs.data.data.list.length) {
							// 获取列表
							this.configList = (rs.data.data.list || []).filter(item => item.id == 10 || item.id == 13 ||item.id == 33)
							this.configVisible = true
						} else {
							this.$message.error('无相关配置，请联系管理员')
						}
					})
				}
			};
			document.addEventListener('keydown', handler);
			this._hiddenBtnKeyHandler = handler;
		},
		beforeDestroy() {
			if (this._hiddenBtnKeyHandler) {
				document.removeEventListener('keydown', this._hiddenBtnKeyHandler);
			}
		},
		methods: {
			configNameChange(e) {
				var arr = [{name: '阿里云',value: 'aliyun'},{name: '高德',value: 'gaode'},{name: '微信',value: 'wx'}]
				for(let x in arr) {
					if(arr[x].value == e) {
						return arr[x].name
					}
				}
				return e
			},
			/** 后台管理员会话：sessionTable 为 users（与系统内管理员判断一致） */
			isAdminLoggedIn() {
				const token = this.$storage.get('Token')
				return this.$storage.get('sessionTable') === 'users' && !!token
			},
			configRowClick(e) {
				this.configTitle = e.name
				this.configValueForm = e
				this.configForm = JSON.parse(e.value)
				this.configVisible2 = true
			},
			configSave() {
				this.configValueForm.value = JSON.stringify(this.configForm)
				this.$http.post('config/update', this.configValueForm).then(rs => {
					this.$message.success('操作成功')
					this.configVisible2 = false
				})
			},
		}
	}
</script>

<style>

	* {
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}

	html,
	body {
		width: 100%;
		height: 100%;
	}

	#app {
		height: 100%;
	}

	body {
		padding: 0;
		margin: 0;

	}
</style>