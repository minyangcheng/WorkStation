<template>
  <div class="organe" @click="clickEvent()">
    {{btnTxt}}
  </div>
</template>

<script>
  export default {
    props: {
      btnTxt: String
    },
    methods:{
        clickEvent(){
          this.$emit('click');
        }
    }
  }
</script>

<style scoped>
  .organe{
    color: white;
    font-size: 20px;
    background: #ff702f;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
