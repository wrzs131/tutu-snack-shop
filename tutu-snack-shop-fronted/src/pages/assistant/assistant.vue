<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref, onMounted, nextTick } from 'vue'
import { chatAPI } from '@/services/assistant'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()
const navbarHeight = ref(safeAreaInsets?.top + 64 + 30) // 状态栏 + 标题 + padding-bottom

// 聊天消息列表
const messages = ref([
  {
    id: '1',
    type: 'bot',
    content: '你好！我是你的智能美食助手，有什么可以帮你的吗？',
    time: new Date().toLocaleTimeString()
  }
])

// 加载状态
const isLoading = ref(false)

// 输入消息
const inputMessage = ref('')

// 发送消息
const sendMessage = async () => {
  if (inputMessage.value.trim() && !isLoading.value) {
    const userContent = inputMessage.value.trim()
    
    // 添加用户消息
    messages.value.push({
      id: Date.now().toString(),
      type: 'user',
      content: userContent,
      time: new Date().toLocaleTimeString()
    })
    
    // 清空输入框
    inputMessage.value = ''
    
    // 滚动到底部
    await scrollToBottom()
    
    // 开启加载状态
    isLoading.value = true
    
    try {
      // 调用后端接口
      const res = await chatAPI({
        messages: [
          {
            role: 'user',
            content: userContent
          }
        ]
      })
      
      // 添加机器人回复
      if (res.code === 1 && res.data) {
        messages.value.push({
          id: (Date.now() + 1).toString(),
          type: 'bot',
          content: res.data.content,
          time: new Date().toLocaleTimeString()
        })
      } else {
        messages.value.push({
          id: (Date.now() + 1).toString(),
          type: 'bot',
          content: '抱歉，服务暂时不可用，请稍后再试。',
          time: new Date().toLocaleTimeString()
        })
      }
    } catch (error) {
      // 错误处理
      messages.value.push({
        id: (Date.now() + 1).toString(),
        type: 'bot',
        content: '网络连接失败，请检查网络后重试。',
        time: new Date().toLocaleTimeString()
      })
    } finally {
      // 关闭加载状态
      isLoading.value = false
    }
    
    // 滚动到底部
    await scrollToBottom()
  }
}

// 滚动到底部
const scrollToBottom = async () => {
  await nextTick()
  const messagesContainer = uni.createSelectorQuery().select('.messages')
  if (messagesContainer) {
    messagesContainer.boundingClientRect((data: any) => {
      uni.pageScrollTo({
        scrollTop: data.scrollHeight,
        duration: 300
      })
    }).exec()
  }
}

// 页面加载完成后滚动到底部
onMounted(() => {
  setTimeout(() => {
    scrollToBottom()
  }, 100)
})
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar title="智能助手" :showBack="true" />
  
  <!-- 聊天容器 -->
  <view class="chat-container">
    <!-- 聊天消息列表 -->
    <view class="messages">
      <!-- 机器人消息 -->
      <view v-for="message in messages" :key="message.id" 
        :class="['message-item', message.type === 'bot' ? 'bot-message' : 'user-message']">
        
        <view v-if="message.type === 'bot'" class="message-avatar bot-avatar">🤖</view>
        
        <view class="message-content">
          <view :class="['message-bubble', message.type === 'bot' ? 'bot-bubble' : 'user-bubble']">
            {{ message.content }}
          </view>
          <view class="message-time">{{ message.time }}</view>
        </view>
        
        <view v-if="message.type === 'user'" class="message-avatar user-avatar">👤</view>
      </view>
      
      <!-- 加载动画 -->
      <view v-if="isLoading" class="message-item bot-message">
        <view class="message-avatar bot-avatar">🤖</view>
        <view class="message-content">
          <view class="message-bubble bot-bubble loading-bubble">
            <view class="loading-dots">
              <view class="dot"></view>
              <view class="dot"></view>
              <view class="dot"></view>
            </view>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 输入区域 -->
    <view class="input-container">
      <input 
        v-model="inputMessage" 
        class="input-box" 
        placeholder="请输入您的问题..."
        :disabled="isLoading"
        @confirm="sendMessage"
      />
      <button class="send-btn" :class="{ disabled: isLoading }" @click="sendMessage">
        {{ isLoading ? '思考中...' : '发送' }}
      </button>
    </view>
  </view>
</template>

<style lang="scss">
page {
  height: 100%;
  overflow: hidden;
}

.chat-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #f7f7f7;
  padding-bottom: constant(safe-area-inset-bottom);
  padding-bottom: env(safe-area-inset-bottom);
}

.messages {
  flex: 1;
  padding: 180rpx 20rpx 20rpx;
  overflow-y: auto;
  
  &::-webkit-scrollbar {
    display: none;
  }
}

.message-item {
  display: flex;
  margin-bottom: 30rpx;
  align-items: flex-start;
  
  &.bot-message {
    justify-content: flex-start;
    
    .message-content {
      margin-left: 20rpx;
    }
  }
  
  &.user-message {
    justify-content: flex-end;
    
    .message-content {
      margin-right: 20rpx;
    }
  }
}

.message-avatar {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40rpx;
  flex-shrink: 0;
}

.bot-avatar {
  background-color: #fff;
  border: 2rpx solid #e0e0e0;
}

.user-avatar {
  background-color: #27ba9b;
  color: #fff;
}

.message-content {
  max-width: 70%;
}

.message-bubble {
  padding: 20rpx 28rpx;
  border-radius: 20rpx;
  font-size: 28rpx;
  line-height: 1.5;
  word-wrap: break-word;
}

.bot-bubble {
  background-color: #fff;
  border-top-left-radius: 4rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
}

.user-bubble {
  background-color: #27ba9b;
  color: #fff;
  border-top-right-radius: 4rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
}

.message-time {
  font-size: 20rpx;
  color: #999;
  margin-top: 8rpx;
  text-align: center;
}

/* 加载动画气泡 */
.loading-bubble {
  padding: 30rpx 40rpx;
  min-width: 80rpx;
}

.loading-dots {
  display: flex;
  align-items: center;
  gap: 10rpx;
  
  .dot {
    width: 16rpx;
    height: 16rpx;
    border-radius: 50%;
    background-color: #999;
    animation: bounce 1.4s infinite ease-in-out;
    
    &:nth-child(1) {
      animation-delay: -0.32s;
    }
    
    &:nth-child(2) {
      animation-delay: -0.16s;
    }
    
    &:nth-child(3) {
      animation-delay: 0s;
    }
  }
}

@keyframes bounce {
  0%, 80%, 100% {
    transform: scale(0);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

.input-container {
  display: flex;
  align-items: center;
  padding: 20rpx;
  padding-bottom: calc(20rpx + constant(safe-area-inset-bottom));
  padding-bottom: calc(20rpx + env(safe-area-inset-bottom));
  background-color: #fff;
  border-top: 2rpx solid #f0f0f0;
  gap: 20rpx;
  flex-shrink: 0;
}

.input-box {
  flex: 1;
  height: 72rpx;
  background-color: #f7f7f7;
  border-radius: 36rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
  
  &:disabled {
    opacity: 0.6;
  }
}

.send-btn {
  width: 120rpx;
  height: 72rpx;
  background-color: #27ba9b;
  color: #fff;
  border-radius: 36rpx;
  font-size: 28rpx;
  line-height: 72rpx;
  text-align: center;
  border: none;
  transition: all 0.3s;
  
  &.disabled {
    background-color: #ccc;
    pointer-events: none;
  }
}
</style>