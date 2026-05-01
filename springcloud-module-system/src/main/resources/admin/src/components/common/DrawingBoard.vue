<template>
	<div>
		<canvas ref="canvas" @mousedown="startDrawing" @mouseup="stopDrawing" @mousemove="draw"
			@mouseout="stopDrawing"></canvas>
		<el-button size="mini" type="success" round @click="uploadImage">保存</el-button>
		<el-button size="mini" type="danger" round	 @click="resizeCanvas">清空</el-button>
	</div>
</template>

<script>
	import html2canvas from 'html2canvas';
	import axios from 'axios'
	export default {
		name: 'DrawingBoard',
		data() {
			return {
				isDrawing: false,
				context: null,
				canvas: null,
			};
		},
		mounted() {
			this.canvas = this.$refs.canvas;
			this.context = this.canvas.getContext('2d');
			this.resizeCanvas();
			window.addEventListener('resize', this.resizeCanvas);
		},
		beforeDestroy() {
			window.removeEventListener('resize', this.resizeCanvas);
		},
		methods: {
			resizeCanvas() {
				this.canvas.width = this.canvas.offsetWidth;
				this.canvas.height = this.canvas.offsetHeight;
				this.fillCanvasWhite(); // 保持白色背景，避免透明
			},
			fillCanvasWhite() {
				if (!this.context || !this.canvas) return;
				this.context.save();
				this.context.globalCompositeOperation = 'source-over';
				this.context.fillStyle = '#fff';
				this.context.fillRect(0, 0, this.canvas.width, this.canvas.height);
				this.context.restore();
				this.context.beginPath(); // 填充后重置路径，防止残留
			},
			startDrawing(event) {
				this.isDrawing = true;
				this.draw(event);
			},
			draw(event) {
				if (!this.isDrawing) return;
				this.context.lineWidth = 5; // 线宽
				this.context.lineCap = 'round'; // 线端点样式
				this.context.strokeStyle = '#000'; // 线条颜色
				this.context.lineTo(event.offsetX, event.offsetY); // 绘制到当前鼠标位置
				this.context.stroke(); // 绘制线条
				this.context.beginPath(); // 开始新路径，移动到当前鼠标位置以供下一次绘制使用
				this.context.moveTo(event.offsetX, event.offsetY);
			},
			stopDrawing() {
				this.isDrawing = false;
				this.context.beginPath(); // 停止绘制时重置路径，以防下次绘制出现问题
			},
			uploadImage() {
				const canvas = this.$refs.canvas;
				// 将canvas转换为Blob二进制文件流
				canvas.toBlob((blob) => {
					if (blob) {
						this.uploadToServer(blob);
					} else {
						console.error('Failed to convert canvas to blob');
					}
				}, 'image/png'); // 指定图片格式为PNG
			},
			uploadToServer(blob) {
				const formData = new FormData();
				// 将Blob对象作为文件添加到FormData中，这样会以二进制文件流形式上传
				formData.append('file', blob, 'drawing.png'); // 第三个参数是文件名

				// 使用axios上传图像到服务器
				axios.post(this.$base.url + 'file/upload', formData, {
						headers: {
							'Content-Type': 'multipart/form-data'
						}
					})
					.then(response => {
						this.$emit('change',response.data.file)
					})
					.catch(error => {
						console.error('Error uploading image', error);
					});
			}
		}
	};
</script>

<style scoped>
	canvas {
		border: 1px solid #ccc;
		/* 可选：给canvas添加边框 */
		width: 100%;
		/* 根据需要调整 */
		height: 400px;
		/* 根据需要调整 */
	}
</style>