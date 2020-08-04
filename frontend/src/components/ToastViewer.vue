<template>
  <viewer :initialValue="text"/>
</template>
<script>
  import '@toast-ui/editor/dist/toastui-editor-viewer.css';

  import { Viewer } from '@toast-ui/vue-editor';
  import { mapState } from 'vuex'

  export default {
    name: 'ToastViewer',
    components: {
      viewer: Viewer
    },
    data() {
      return {
          text: ''
      };
    },
    computed: {
      ...mapState(['project'])
    },
    watch: {
      project() {
        console.log(this.project)
        this.text = this.project.content
      }
    }
  };
</script>