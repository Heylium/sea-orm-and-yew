<script setup lang="ts">
import {computed, inject} from 'vue'
import type {CollapseItemProps} from "./types.ts";
import { collapseContextKey } from "./types.ts";
import Icon from "../Icon/Icon.vue"


defineOptions({
  name: 'VkCollapseItem',
})

const props = defineProps<CollapseItemProps>();

const collapseContext = inject(collapseContextKey)
const isActive = computed(() => collapseContext?.activeNames.value.includes(props.name))
const handleClick = () => {
  if (props.disabled) return
  collapseContext?.handleItemClick(props.name)
}
const transitionEvents: Record<string, (el: HTMLElement) => void> = {
  beforeEnter(el) {
    el.style.height = '0px'
    el.style.overflow = 'hidden'
  },
  enter(el) {
    el.style.height = `${el.scrollHeight}px`
  },
  afterEnter(el) {
    el.style.height = ``
    el.style.overflow = ''
  },
  beforeLeave(el) {
    el.style.height = `${el.scrollHeight}px`
    el.style.overflow = 'hidden'
  },
  leave(el) {
    el.style.height = '0px'
  },
  afterLeave(el) {
    el.style.height = ''
    el.style.overflow = ''
  }
}
</script>

<template>
<div
  class="vk-collapse-item"
>
  <div class="vk-collapse-item__header"
       :class="{
        'is-disabled': disabled,
        'is-active': isActive,
        }"
       :id="`item-header-${name}`"
       @click="handleClick">
    <slot name="title">{{title}}</slot>
    <Icon icon="angle-right" class="header-angle" />
  </div>
  <Transition name="fade" v-on="transitionEvents">
    <div class="vk-collapse-item__wrapper" :id="`item-content-${name}`"  v-show="isActive">
      <div class="vk-collapse-item__content" >
        <slot></slot>
      </div>
    </div>
  </Transition>
</div>
</template>

<style scoped>
.vk-collapse-item__header {
  font-size: 30px;
}

</style>