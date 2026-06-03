<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref } from 'vue'

const feedbackContent = ref('')

const handleSubmit = () => {
  if (!feedbackContent.value.trim()) {
    uni.showToast({
      title: '请输入反馈内容',
      icon: 'none'
    })
    return
  }

  uni.showToast({
    title: '已发送',
    icon: 'success'
  })

  feedbackContent.value = ''
}
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar title="意见反馈" :showBack="true" />

  <view class="viewport">
    <view class="container">
      <view class="tip">请详细描述您遇到的问题或建议，我们会认真对待每一条反馈</view>

      <textarea
        class="feedback-input"
        v-model="feedbackContent"
        placeholder="请输入您的意见或建议..."
        :maxlength="500"
        auto-height
      />

      <view class="char-count">{{ feedbackContent.length }}/500</view>

      <button class="submit-btn" @click="handleSubmit">确认发送</button>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f5f5f5;
  font-size: 28rpx;
  color: #333;
  margin-top: 60px;
}

.viewport {
  padding: 30rpx;
}

.container {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
}

.tip {
  font-size: 26rpx;
  color: #999;
  margin-bottom: 30rpx;
  line-height: 1.6;
}

.feedback-input {
  width: 100%;
  min-height: 300rpx;
  background-color: #f8f8f8;
  border-radius: 12rpx;
  padding: 24rpx;
  font-size: 28rpx;
  color: #333;
  line-height: 1.6;
  box-sizing: border-box;
}

.char-count {
  text-align: right;
  font-size: 24rpx;
  color: #999;
  margin-top: 16rpx;
}

.submit-btn {
  margin-top: 60rpx;
  width: 100%;
  height: 90rpx;
  background-color: #ff9800;
  color: #fff;
  font-size: 32rpx;
  font-weight: 500;
  border-radius: 45rpx;
  border: none;
}
</style>
