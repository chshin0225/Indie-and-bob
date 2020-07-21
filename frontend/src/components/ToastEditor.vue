<template>
    <editor
      :initialValue="editorText"
    :options="editorOptions"
    initialEditType="wysiwyg"
    previewStyle="vertical" />


</template>

<script>
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

import { Editor } from '@toast-ui/vue-editor';

export default {
  name: 'ToastEditor',
    props: {
        editor: Object,
    },

  components: {
    editor: Editor
  },
      data() {
      return {
        editorOptions: {
          hideModeSwitch: true
        }
      };
    }
};
</script>

<style>

</style>




