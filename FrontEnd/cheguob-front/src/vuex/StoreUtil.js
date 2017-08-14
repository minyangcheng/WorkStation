/**
 * Created by cheguo on 2017/5/5.
 *
 * 根据state中的变量动态生成mutations便捷方法
 */
export default {
  // 生成 mutations 方法
  generateMethod(name) {
    return function (state, value) {
      state[name] = value;
    }
  },
  generateMutation(options){
    Object.keys(options.state).forEach((key) => {
      options.mutations[key] = this.generateMethod(key);
    });
  }

}