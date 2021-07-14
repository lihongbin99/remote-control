<template>
  <div class="hello">
    <div>
      <img class="emojiBox" :style="styleImage" :src="imageSrc" alt=""
           @mousedown="mousedown"
           @mouseup="mouseup"
           @touchstart="touchstart"
           @touchend="touchend"
      >
    </div>
    <div>
      <el-button @click="keyEvent('WAKEUP')">打开屏幕</el-button>
      <el-button @click="keyEvent('POWER')">关闭屏幕</el-button>
      <el-button @click="keyEvent('APP_SWITCH')">任务列表</el-button>
      <el-button @click="keyEvent('HOME')">主页</el-button>
      <el-button @click="keyEvent('BACK')">返回</el-button>
      <el-button @click="keyEvent('MENU')">菜单(解锁)</el-button>
    </div>
  </div>
</template>

<script>
import axios from '../utils/axios';

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      imageSrc: 'data:image/jpeg;base64,',
      refreshCount: 0,
      startSize: {},
      endSize: {},
      imageHeight: null,
      styleImage: {
        '-webkit-user-select': 'none',
        '-moz-user-select': 'none',
        '-o-user-select': 'none',
        'user-select': 'none',
        height: (document.documentElement.clientHeight - 50) + "px"
      },
    }
  },
  created() {
    this.init();
    this.screencap();
  },
  mounted () {
    const _this = this
    window.onresize = () => {
      return (() => {
        _this.init();
      })()
    }
  },
  methods: {
    init() {
      this.styleImage.imageHeight = (document.documentElement.clientHeight - 50)
      this.styleImage.height = this.styleImage.imageHeight + "px"
    },
    screencap() {
      const _this = this;
      setTimeout(() => {
        axios.get("screencap").then(result => {
          _this.imageSrc = "data:image/jpeg;base64," + result.data.data;
          _this.refreshCount += 1;
          _this.screencap();
        }).catch(error => {
          this.$message.error(error)
        })
      }, 0);
    },
    stopBubble(e) {
      if(e.stopPropagation) e.stopPropagation();
      if(e.preventDefault) e.preventDefault();
      e.cancelBubble=true;
      e.returnValue=false;
      return false;
    },
    mousedown(e) {
      const x = e.offsetX;
      const y = e.offsetY;
      this.startSize = {x, y}
      return this.stopBubble(e);
    },
    mouseup(e) {
      const x = e.offsetX;
      const y = e.offsetY;
      this.endSize = {x, y}
      this.click();
      return this.stopBubble(e);
    },
    touchstart(e) {
      const rect = e.target.getBoundingClientRect();
      const x = e.changedTouches[0].pageX - rect.left;
      const y = e.changedTouches[0].pageY - rect.top;
      this.startSize = {x, y}
      return this.stopBubble(e);
    },
    touchend(e) {
      const rect = e.target.getBoundingClientRect();
      const x = e.changedTouches[0].pageX - rect.left;
      const y = e.changedTouches[0].pageY - rect.top;
      this.endSize = {x, y}
      this.click();
      return this.stopBubble(e);
    },
    click() {
      if (this.refreshCount < 2) {
        this.$message.error('操作频繁')
        return false;
      }
      this.refreshCount = 0;

      // 判断是点击还是滑动
      if (Math.abs(this.startSize.x - this.endSize.x) < 50 && Math.abs(this.startSize.y - this.endSize.y) < 50) {
        const data = {
          x: this.endSize.x,
          y: this.endSize.y,
          imageHeight: this.styleImage.imageHeight,
        }
        axios.post("tap", data).then(result => {
          this.$message.success('点击成功')
        }).catch(error => {
          this.$message.error(error)
        })
      } else {
        const data = {
          sx: this.startSize.x,
          sy: this.startSize.y,
          ex: this.endSize.x,
          ey: this.endSize.y,
          imageHeight: this.styleImage.imageHeight,
        }
        axios.post("swipe", data).then(result => {
          this.$message.success('滑动成功')
        }).catch(error => {
          this.$message.error(error)
        })
      }
    },
    keyEvent(keyEventCode) {
      const data = {
        keyEventCode
      }
      axios.post("keyevent", data).then(result => {
        this.$message.success('输入成功')
      }).catch(error => {
        this.$message.error(error)
      })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
