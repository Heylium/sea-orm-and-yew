<script setup lang="ts">
import type {InputEmits, InputProps} from "./types.ts";
import VkIcon from "../Icon/Icon.vue";
import {computed, inject, nextTick, Ref, ref, useAttrs, watch} from "vue";


defineOptions({
  name: 'Input',
})

const props = withDefaults(defineProps<InputProps>(), {
  type: 'text',
  autocomplete: 'off',
})

const emits = defineEmits<InputEmits>()
const attrs = useAttrs()
const innerValue = ref(props.modelValue)
const isFocus = ref(false)
const passwordVisible = ref(false)
const inputRef = ref() as Ref<HTMLInputElement>
// const formItemContext = inject(formItemContextKey)
// const runValidation = (trigger?: string) => {
//   formItemContext?.validate(trigger).catch((e) => console.log(e.errors))
// }
const showClear = computed(() =>
    props.clearable &&
    !props.disabled &&
    !!innerValue.value &&
    isFocus.value
)
const showPasswordArea = computed(() =>
    props.showPassword &&
    !props.disabled &&
    !!innerValue.value
)
const togglePasswordVisible = () => {
  passwordVisible.value = !passwordVisible.value
}
const NOOP = () => {}
const keepFocus = async () => {
  await nextTick()
  inputRef.value.focus()
}
const handleInput = () => {
  emits('update:modelValue', innerValue.value)
  emits('input', innerValue.value)
  // runValidation('input')
}
const handleChange = () => {
  emits('change', innerValue.value)
  // runValidation('change')
}
const handleFocus = (event: FocusEvent) => {
  isFocus.value = true
  emits('focus', event)
}
const handleBlur = (event: FocusEvent) => {
  console.log('blur triggered')
  isFocus.value = false
  emits('blur', event)
  // runValidation('blur')
}
const clear = () => {
  console.log('clear triggered')
  innerValue.value = ''
  emits('update:modelValue', '')
  emits('clear')
  emits('input', '')
  emits('change', '')
}
watch(() => props.modelValue, (newValue) => {
  innerValue.value = newValue
})
defineExpose({
  ref: inputRef
})
</script>

<template>
  <div
      class="vk-input"
      :class="{
    [`vk-input--${type}`]: type,
    [`vk-input--${size}`]: size,
    'is-disabled': disabled,
    'is-prepend': $slots.prepend,
    'is-append': $slots.append,
    'is-prefix': $slots.prefix,
    'is-suffix': $slots.suffix,
    'is-focus': isFocus
  }"
  >
    <!-- input -->
    <template v-if="type !== 'textarea'">
      <!-- prepend slot -->
      <div v-if="$slots.prepend" class="vk-input__prepend">
        <slot name="prepend" />
      </div>
      <div class="vk-input__wrapper">
        <!-- prefix slot -->
        <span v-if="$slots.prefix" class="vk-input__prefix">
          <slot name="prefix" />
      </span>
        <input
            class="vk-input__inner"
            :type="showPassword ? (passwordVisible ? 'text' : 'password') : type"
            ref="inputRef"
            v-bind="attrs"
            :disabled="disabled"
            :readonly="readonly"
            :autocomplete="autocomplete"
            :placeholder="placeholder"
            :autofocus="autofocus"
            :form="form"
            v-model="innerValue"
            @input="handleInput"
            @change="handleChange"
            @focus="handleFocus"
            @blur="handleBlur"
        />
        <!-- suffix slot -->
        <span v-if="$slots.suffix || showClear || showPasswordArea" class="vk-input__suffix" @click="keepFocus">
          <slot name="suffix" />
          <VkIcon
              icon="circle-xmark"
              v-if="showClear"
              class="vk-input__clear"
              @click="clear"
              @mousedown.prevent="NOOP"
          />
          <VkIcon
              icon="eye"
              v-if="showPasswordArea && passwordVisible"
              class="vk-input__password"
              @click="togglePasswordVisible"
          />
          <VkIcon
              icon="eye-slash"
              v-if="showPasswordArea && !passwordVisible"
              class="vk-input__password"
              @click="togglePasswordVisible"
          />
      </span>
      </div>
      <!-- append slot -->
      <div v-if="$slots.append" class="vk-input__append">
        <slot name="append" />
      </div>
    </template>
    <!-- textarea -->
    <template v-else>
    <textarea
        class="vk-textarea__wrapper"
        v-bind="attrs"
        ref="inputRef"
        :disabled="disabled"
        :readonly="readonly"
        :autocomplete="autocomplete"
        :placeholder="placeholder"
        :autofocus="autofocus"
        :form="form"
        v-model="innerValue"
        @input="handleInput"
        @change="handleChange"
        @focus="handleFocus"
        @blur="handleBlur"
    />
    </template>
  </div>
</template>

<style scoped>

</style>