<template>
  <v-container>
      <h1 class='my-5 text-center'>Step 1/3. 프로젝트 개요를 입력해주세요!</h1>
      <v-row class='justify-center'>
         <v-col
            class="py-0" sm=10>
          <label for="title">제목</label>
          <v-text-field
            v-model="title"
            id="extraAddress"
            placeholder="프로젝트 제목을 입력해주세요"
            type="title"
            hide-details=true
            outlined
            required
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="justify-center"> 
        <v-col class='py-0 mt-5' sm=10>
        <label for="content">프로젝트 내용</label>
          <editor
            ref="toastuiEditor"
            :options="editorOptions"
            id="content"
            initialEditType="wysiwyg"
            previewStyle="vertical" />
        </v-col>
      </v-row>
       <v-row justify="center">
           <v-col sm=10>
      <v-menu
        v-model="menu2"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="date"
            label="마감날짜"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker v-model="date" :min="today" @input="menu2 = false"></v-date-picker>
      </v-menu>
    </v-col>
  </v-row>



      <ToastViewer />


  </v-container>
</template>

<script>
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

import { Editor } from '@toast-ui/vue-editor';

export default {
  components: {
    editor: Editor, 
  },
  data() {
    return {
      text : '',
      today:  new Date().toISOString().substr(0, 10),
       date: new Date().toISOString().substr(0, 10),
       title: '',
       menu2: false,
       editorOptions: {
          hideModeSwitch: true,
          
        },
       content: '',
    }
  },
  }
</script>

<style>

</style>