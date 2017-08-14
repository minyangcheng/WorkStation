<template>
  <div class="item-input">
    <span ref="titleRel" class="item-input-left">{{leftText}}</span>
    <input ref="inputRel" class="item-input-right" type="text" :placeholder="placeholder" :value="rightText"
              @focus="inputFocus" @blur="inputBlur" @input="inputChange"/>
    <span ref="clearRel" class="input-clear" @click="clickClear"/>
  </div>
</template>

<script>
  export default {
    props:{
      leftText:{
        type:String,
        required: true,
      },
      rightText:{
        type:String,
      },
      placeholder:{
        type:String,
      },
      inputType:{
        type:Number,
      },
      enable:{
        type:Boolean,
        default:true
      }
    },
    data(){
      return {
        name:"minyangchneg"
      }
    },
    computed: {
    },
    methods:{
      clickClear(){
        $(this.$refs.inputRel).focus();
        $(this.$refs.inputRel).val("");
        $(this.$refs.clearRel).css("display","none");
      },
      inputFocus(){
        if(!($(this.$refs.inputRel).val())){
          $(this.$refs.clearRel).css("display","none");
        }else{
          $(this.$refs.clearRel).css("display","block");
        }
      },
      inputBlur(){
        var self=this;
        setTimeout(function () {
          $(self.$refs.clearRel).css("display","none");
        },100)
      },
      inputChange(){
        if(!($(this.$refs.inputRel).val())){
          $(this.$refs.clearRel).css("display","none");
        }else{
          $(this.$refs.clearRel).css("display","block");
        }
      },
      getValue(){
        return $(this.$refs.inputRel).val();
      },
      setValue(value){
        $(this.$refs.inputRel).val(value);
      }
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    watch:{
      enable(){
        if(!this.enable){
          this.$refs.inputRel.setAttribute("disabled","disabled");
        }
      }
    },
    components:{},
  }
</script>

<style lang="less" type="text/less" scoped>
  @import "./../assets/css/common.less";
  @import "../assets/css/color.less";
  @import "../assets/css/dimen.less";

  .item-input{
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    padding: 0 @itemPaddingLR;
    width: 100%;
    height: @itemHeight;
    background: @white;
  }
  .item-input-left{
    width: 60px;
    font-size: 15px;
    color: #222222;
    .single-line;
  }
  .item-input-right{
    flex: 1;
    padding-left: 15px;
    outline: 0;
    border: 0;
    text-align: left;
    font-size: 15px;
    color: #222222;
    .single-line;
    background: rgba(0, 0, 0, 0);
  }
  .input-clear {
    display: none;
    width: 14px;
    height: 14px;
    background: url(../assets/img/clear.png) no-repeat;
    background-size: cover;
  }
</style>
