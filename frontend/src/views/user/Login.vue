<template>
  <v-container>
    <h1 class="text-center">Login</h1>

    <!-- email -->
    <v-row class="justify-center">
      <v-col class="py-0" sm=6>
        <label for="email">이메일</label>
        <v-text-field
          class="my-3"
          hide-details=true
          v-model="email"
          @keyup.enter="login({email: email, password: password})"
          id="email"
          outlined
          placeholder="이메일을 입력하세요."
          type="text"
        />
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </v-col>
    </v-row>

    <!-- password -->
    <v-row class="justify-center">
      <v-col class="py-0" sm=6>
        <label for="password">비밀번호</label>
          <v-text-field
          class="my-3"
          hide-details=true
          v-model="password"
          id="password"
          @keyup.enter="login({email: email, password: password})"
          outlined
          placeholder="비밀번호를 입력하세요."
          type="password"
        />
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </v-col>
    </v-row>

    <!-- login btn -->
    <v-row class="justify-center">
      <v-col class="py-0 text-right" sm=6>
        <v-btn
          @click="login({email: email, password: password})"
          :disabled="!isSubmit"
          :class="{disabled : !isSubmit}"
          depressed
          large
          color="primary"
        >로그인</v-btn>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols=12 class="text-center">
        <div class="wrap">
          <p>혹시 비밀번호를 잊으셨나요?</p>
        </div>

        <div class="wrap">
          <p>아직 회원이 아니신가요?</p>
          <router-link to="/user/join" class="v-btn">가입하기</router-link>
        </div>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import { mapActions, mapState } from 'vuex';

export default {
  name: 'Login',

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
    password: function() {
      this.checkForm();
    },
    email: function() {
      this.checkForm();
      if (this.email.length>0 && this.email.charAt(0)>='A' && this.email.charAt(0) <='Z') {
      this.email = this.email.substring(0,1).toLowerCase() + this.email.substring(1)
        }
    }
  },
  mounted () {
    this.email = this.oriEmail
    this.password = this.oriPassword
  },
  methods: {
    ...mapActions([ 'login', ]),

    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
  },

  data() {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false
      },
      isSubmit: false,
      component: this,
    };
  },
  computed : {
    ...mapState([ "oriEmail", "oriPassword" ])
  }
};
</script>

<style scoped>

</style>