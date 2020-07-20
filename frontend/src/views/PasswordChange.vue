<template>
  <v-container>
    <h1 class="text-center">비밀번호 변경</h1>

    <!-- old password -->
    <v-row class="justify-center">
      <v-col class="py-0" sm=6>
        <label for="old-password">현재 비밀번호</label>
        <v-text-field
         class="my-3"
         hide-details=true
         v-model="oldPassword" 
         id="old-password" 
         outlined
         type="password" 
         placeholder="현재 비밀번호를 입력하세요." 
        />
        <small class="d-block" v-if="error.oldPassword">{{ error.oldPassword }}</small>
      </v-col>
    </v-row>
    
    <!-- new password -->
    <v-row class="justify-center">
      <v-col class="py-0" sm=6>
        <label for="new-password">새 비밀번호</label>
        <v-text-field
         class="my-3"
         hide-details=true
         v-model="password" 
         id="new-password" 
         outlined
         type="password" 
         placeholder="새 비밀번호를 입력하세요." 
        />
        <small class="d-block" v-if="error.password">{{ error.password }}</small>
      </v-col>
    </v-row>

    <!-- new password confirmation -->
    <v-row class="justify-center">
      <v-col class="py-0" sm=6>
        <label for="new-password-confirm">새 비밀번호 확인</label>
        <v-text-field
          class="my-3"
          hide-details=true
          v-model="passwordConfirm"
          type="password"
          outlined
          id="new-password-confirm"
          placeholder="새 비밀번호를 다시한번 입력하세요."
        />
        <small class="d-block" v-if="error.passwordConfirm">{{ error.passwordConfirm }}</small>
      </v-col>
    </v-row>

    <!-- submit btn -->
    <v-row class="justify-center">
      <v-col class="py-0 text-right" sm=6>
        <v-btn
          @click="changePassword({oripw:oldPassword, newpw:password})"
          :disabled="!isSubmit"
          :class="{disabled : !isSubmit}"
          depressed
          large
          color="primary"
        >비밀번호 변경</v-btn>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import PV from "password-validator";
import {mapActions} from 'vuex'

export default {
  created() {
  this.component = this;
  this.passwordSchema
    .is()
    .min(8)
    .is()
    .max(100)
    .has()

    .digits()
    .has()
    .letters();
}, 
  watch: {
  oldPassword : function() {
    this.checkForm();
  },
  password: function() {
    this.checkForm();
  },
  passwordConfirm: function() {
    this.checkForm();
  }
},
  data() {
    return {
      isSubmit: false,
      password: "",
      passwordConfirm: "",
      oldPassword: "",
      passwordSchema: new PV(),
      error: {
        password: false,
        passwordConfirm: false,
      },
    }
  },

  methods: {
    ...mapActions(['changePassword']),
    checkForm() {
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      if (
        this.passwordConfirm != this.password
      )
       this.error.passwordConfirm = "비밀번호와 비밀번호 확인이 일치하지 않습니다."
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },

  },

}
</script>

<style>

</style>