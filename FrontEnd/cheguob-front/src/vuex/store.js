import StoreUtil from './StoreUtil';

const options = {
  state: {
    message: 'are you ok?',
  },
  getters: {
    formatMsg: state => {
      return state.message.toUpperCase();
    }
  },
  mutations: {},
  actions: {},
};

StoreUtil.generateMutation(options);

export default options;
