<template>
    <v-container>
      <editor
    v-model='text'
     ref="toastuiEditor"
    :options="editorOptions"
    initialEditType="wysiwyg"
    previewStyle="vertical" />
      <v-btn
      depressed
      @click="createAction"
      >저장하기</v-btn>
    </v-container>

    


</template>

<script>
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

import { Editor } from '@toast-ui/vue-editor';

export default {
  name: 'ToastEditor',
  components: {
    editor: Editor
  },
      data() {
      return {
        editorOptions: {
          hideModeSwitch: true,
          
        },
        text:''};

    },
  methods: { 
    createAction() {
      this.text = this.$refs.toastuiEditor.invoke("getHtml");
      console.log(this.text)
      }
  }
}
</script>

<style>

</style>




