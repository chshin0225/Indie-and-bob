import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
  // color theme 설정 
  theme: {
    themes: {
      light: {
        primary: '#c1666b',
        secondary: '#e4dfda',
        accent: '#d4b483',
      },
    },
  },
});
