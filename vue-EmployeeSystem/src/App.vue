<template>
  <!-- 路由出口：用来显示登录页、首页等所有页面 -->
  <router-view />
</template>

<script setup lang="ts">
// 这里可以留空，只需要保证有 script 标签即可
</script>

<style>
/* 全局样式重置，和 global.css 互补 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>