import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
  // color theme 설정 
  theme: {
    themes: {
      light: {
        primary: '#c1666bi',
        secondary: '#e4dfda',
        accent: '#d4b483',
        success: '#4CAF50',
      },
    },
  },
});


// primary: '#c1666bi'