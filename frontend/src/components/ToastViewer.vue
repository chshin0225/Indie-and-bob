<template>
  <viewer :initialValue="text"/>
</template>

<script>
  import '@toast-ui/editor/dist/toastui-editor-viewer.css';
  import { Viewer } from '@toast-ui/vue-editor';

  export default {
    name: 'ToastViewer',

    components: {
      viewer: Viewer
    },

    data() {
      return {
          text: `
         
          `
      };
    }
  };
</script>