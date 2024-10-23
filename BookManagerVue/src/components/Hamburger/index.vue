<!--
 * @Description: 
 * @Author: 
 * @Date: 2024-02-21 18:13:21
-->
<template>
  <div style="padding: 0 15px;" @click="toggleClick">
    <i class="iconfont icon-r-left" :class="{'icon-r-right':!isActive}" style="font-size: 26px;"></i>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  props: {
    isActive: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    toggleClick() {
      this.$emit('toggleClick')
    }
  }
}
</script>

<style scoped>
.hamburger {
  display: inline-block;
  vertical-align: middle;
  width: 20px;
  height: 20px;
}

.hamburger.is-active {
  transform: rotate(180deg);
}
</style>
