<template>
	<div>
		<!-- 上传文件组件 -->
		<el-upload :disabled="disabled" v-if="type==1" ref="upload" :action="getActionUrl" list-type="picture-card"
			:multiple="multiple" :limit="limit" :headers="myHeaders" :file-list="fileList" :on-exceed="handleExceed"
			:on-preview="handleUploadPreview" :on-remove="handleRemove" :on-success="handleUploadSuccess"
			:on-error="handleUploadErr" :timeout="600000">
			<i class="el-icon-plus"></i>
			<div slot="tip" class="el-upload__tip">{{tip}}</div>
		</el-upload>
		<el-upload :disabled="disabled" v-else-if="type==2||type==3" drag ref="upload" :action="getActionUrl" :multiple="multiple"
			:limit="limit" :accept="accept" :headers="myHeaders" :file-list="fileList" :on-exceed="handleExceed"
			:on-preview="handleUploadPreview" :on-remove="handleRemove" :on-success="handleUploadSuccess"
			:on-error="handleUploadErr" :timeout="600000">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			<div slot="tip" class="el-upload__tip">{{tip}}</div>
		</el-upload>
		<el-upload :disabled="disabled" v-else drag ref="upload" :action="getActionUrl" :multiple="multiple"
			:limit="limit" :accept="accept" :headers="myHeaders" :file-list="fileList" :on-exceed="handleExceed"
			:on-preview="handleUploadPreview" :on-remove="handleRemove" :on-success="handleUploadSuccess"
			:on-error="handleUploadErr" :timeout="600000" :before-upload="handleBeforeUpload">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			<div slot="tip" class="el-upload__tip">{{tip}}</div>
		</el-upload>
		<el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
			<img width="100%" v-if="type==1" :src="dialogImageUrl" alt>
			<video width="100%" v-if="type==2" :src="dialogImageUrl" alt controls />
		</el-dialog>
	</div>
</template>
<script>
	import storage from "@/utils/storage";
	import base from "@/utils/base";
	export default {
		data() {
			return {
				// 查看大图
				dialogVisible: false,
				// 查看大图
				dialogImageUrl: "",
				// 组件渲染图片的数组字段，有特殊格式要求
				fileList: [],
				fileUrlList: [],
				myHeaders: {}
			};
		},
		props: {
			tip: {
				type: String
			},
			action: {
				type: String
			},
			/*图片大小*/
			limit: {
				type: Number,
				default: 3 //kb
			},
			multiple: {
				type: Boolean,
				default: false
			},
			fileUrls: {
				type: String
			},
			type: {
				type: Number,
				default: 1
			},
			disabled: {
				type: Boolean,
				default: false
			},
			accept: {
				type: String
			}
		},
		mounted() {
			this.init();
			this.myHeaders = {
				'Token': storage.get("Token")
			}
		},
		watch: {
			fileUrls: function(val, oldVal) {
				//   console.log("new: %s, old: %s", val, oldVal);
				this.init();
			}
		},
		computed: {
			// 计算属性的 getter
			getActionUrl: function() {
				// return base.url + this.action + "?token=" + storage.get("token");
				return `/${this.$base.name}/` + this.action;
			}
		},
		methods: {
			// 初始化
			init() {
				let that = this
				if (this.fileUrls) {
					this.fileUrlList = this.fileUrls.split(",w").length>1?this.fileUrls:this.fileUrls.split(',');
					let fileArray = [];
					this.fileUrlList.forEach(function(item, index) {
						var url = item;
						var name = that.type==4?'检测文件':index;
						var file = {
							name: name,
							url: url
						};
						fileArray.push(file);
					});
					this.setFileList(fileArray);
				}
			},
			async handleBeforeUpload(file) {
				let that = this
				return new Promise(async(resolve, reject) => {
					const isLt3M = file.size / 1024 / 1024 < 3;
					if (!isLt3M) {
						that.$message.error('文件大小不能超过3MB!');
						reject()
						return false
					}
					if (file.type == 'video/mp4') {
						try {
							const duration = await that.getAudioDuration(file);
							if (duration > 60) { // 300秒=5分钟
								that.$message.error('视频时长不能超过1分钟!');
								reject()
								return false
							}
						} catch (error) {
							that.$message.error('视频解析失败');
							reject()
							return false
						}
					}
					resolve()
				});
			},
			getAudioDuration(file) {
				return new Promise((resolve, reject) => {
					// 创建一个 video 元素来读取文件
					const video = document.createElement('video');
					const url = URL.createObjectURL(file);
					video.src = url;
					
					// 等待视频元数据加载完成
					video.onloadedmetadata = () => {
						resolve(video.duration)
						// 这里可以处理你的业务逻辑，比如将时长存储或发送到服务器等
						URL.revokeObjectURL(url); // 释放 Object URL
					};
					
					// 确保在不再需要视频时释放资源
					video.onerror = () => {
						reject()
						console.error('视频加载失败');
						URL.revokeObjectURL(url); // 释放 Object URL
					};
				});
			},
			// 上传文件成功后执行
			handleUploadSuccess(res, file, fileList) {
				if (res && res.code === 0) {
					fileList[fileList.length - 1]["url"] = "upload/" + file.response.file;
					this.setFileList(fileList);
					this.$emit("change", this.fileUrlList.join(","));
				} else {
					this.$message.error(res.msg);
				}
			},
			// 图片上传失败
			handleUploadErr(err, file, fileList) {
				this.$message.error("文件上传失败");
			},
			// 移除图片
			handleRemove(file, fileList) {
				this.setFileList(fileList);
				this.$emit("change", this.fileUrlList.join(","));
			},
			// 查看大图
			handleUploadPreview(file) {
				if(this.type>2){
					window.open(file.url)
					return false
				}
				this.dialogImageUrl = file.url;
				this.dialogVisible = true;
			},
			// 限制图片数量
			handleExceed(files, fileList) {
				this.$message.warning(`最多上传${this.limit}张图片`);
			},
			// 重新对fileList进行赋值
			setFileList(fileList) {
				var fileArray = [];
				var fileUrlArray = [];
				// 有些图片不是公开的，所以需要携带token信息做权限校验
				var token = storage.get("token");
				let _this = this;
				fileList.forEach(function(item, index) {
					var url = item.url.split("?")[0];
					if (!url.startsWith("http")) {
						url = _this.$base.url + url
					}
					var name = item.name;
					var file = {
						name: name,
						url: url + "?token=" + token
					};
					fileArray.push(file);
					fileUrlArray.push(url);
				});
				this.fileList = fileArray;
				this.fileUrlList = fileUrlArray;
			}
		}
	};
</script>
<style lang="scss" scoped>
</style>