<template>
  <v-container>
    <h1 class="text-center">Signup</h1>

    <!-- id -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="nickname">닉네임</label>
        <v-text-field
          hide-details="true"
          class=""
          v-model="nickName"
          id="nickname"
          outlined
          placeholder="닉네임을 입력해주세요."
          type="text"
        />
        <small class="d-block primary--text" v-if="error.nickName">{{ error.nickName }}</small>
      </v-col>
    </v-row>

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="usertype">가장 좋아하는 장르</label>
        <v-select
          hide-details="true"
          :items="genres"
          id="usertype"
          placeholder="본인이 가장 좋아하는 게임 장르를 선택해주세요."
          v-model="genre"
          outlined
          multiple
          chips
        ></v-select>
      </v-col>
    </v-row>

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="usertype">주 사용자 유형</label>
        <v-select
          hide-details="true"
          :items="userTypes"
          id="usertype"
          placeholder="주 사용자 유형을 선택해주세요."
          v-model="usertype"
          outlined
        ></v-select>
      </v-col>
    </v-row>

    <!-- bank account -->
    <div class="bank-info" v-if="usertype=='개발자'">
      <v-row class="justify-center my-4">
        <v-col class="py-0" sm="2">
          <label for="bankname">은행명</label>
          <v-select
            class=""
            hide-details="true"
            :items="banks"
            id="bankname"
            v-model="bankname"
            outlined
          ></v-select>
        </v-col>
        <v-col class="py-0" sm="4">
          <label for="accountnumber">계좌번호</label>
          <v-text-field
            hide-details="true"
            class=""
            v-model="accountnumber"
            id="accountnumber"
            outlined
            placeholder="(-)를 제외한 숫자만 입력해주세요."
            type="text"
          />
          <small class="d-block primary--text" v-if="error.accountnumber">{{ error.accountnumber }}</small>
        </v-col>
      </v-row>
    </div>

    <!-- email -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="email">이메일</label>
        <v-text-field
          class=""
          hide-details="true"
          v-model="email"
          id="email"
          outlined
          placeholder="이메일 형식을 지켜주세요."
          type="text"
        />
        <small class="d-block primary--text" v-if="error.email">{{ error.email }}</small>
      </v-col>
    </v-row>

    <!-- name -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="name">이름</label>
        <v-text-field
          class=""
          hide-details="true"
          v-model="name"
          id="name"
          outlined
          placeholder="이름을 입력해주세요."
          type="text"
        />
      </v-col>
    </v-row>

    <!-- profile image -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="profile-image">프로필 사진</label>
        <v-file-input
          class=""
          accept="image/*"
          hide-details="true"
          v-model="profileImage"
          id="profile-image"
          outlined
          placeholder="없어도됩니다."
        />
      </v-col>
    </v-row>

    <!-- password -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="password">비밀번호</label>
        <v-text-field
          class=""
          hide-details="true"
          v-model="password"
          :append-icon="showPw ? 'mdi-eye' : 'mdi-eye-off'"
          id="password"
          outlined
          :type="showPw ? 'text' : 'password'"
          placeholder="영소문자와 숫자를 합쳐 8글자 이상 가능합니다."
          @click:append="showPw = !showPw"
        />
        <small class="d-block primary--text" v-if="error.password">{{ error.password }}</small>
      </v-col>
    </v-row>

    <!-- password confirmation -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="password-confirm">비밀번호 확인</label>
        <v-text-field
          class=""
          hide-details="true"
          v-model="passwordConfirm"
          :append-icon="showPwc ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPwc ? 'text' : 'password'"
          outlined
          id="password-confirm"
          placeholder="비밀번호와 일치해야합니다."
          @click:append="showPwc = !showPwc"
        />
        <small class="d-block primary--text" v-if="error.passwordConfirm">{{ error.passwordConfirm }}</small>
      </v-col>
    </v-row>

    <!-- phone number -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="phonenumber">핸드폰 번호('-' 제외)</label>
        <v-text-field
          hide-details="true"
          class=""
          v-model="phonenumber"
          id="phonenumber"
          outlined
          placeholder="휴대폰 번호를 입력해주세요."
          type="text"
        />
        <small class="d-block primary--text" v-if="error.phonenumber">{{ error.phonenumber }}</small>
      </v-col>
    </v-row>

    <!-- introduction -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="introduction">한 줄 소개</label>
        <v-text-field
          class=""
          hide-details="true"
          v-model="introduction"
          id="introduction"
          outlined
          placeholder="자신을 소개해주세요~"
          type="text"
        />
      </v-col>
    </v-row>

    <!-- daum address -->
    <v-row
      ref="searchWindow"
      :style="searchWindow"
      style="border:1px solid;width:100%;margin:5px 0;position:relative"
      class="my-4"
    >
      <v-col class="py-0" sm="6">
        <img
          src="//t1.daumcdn.net/postcode/resource/images/close.png"
          id="btnFoldWrap"
          style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1"
          @click="searchWindow.display = 'none'"
          alt="close"
        />
      </v-col>
    </v-row>
    
    <!-- 우편번호 -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="3">
        <label for="postcode">우편번호</label>
        <v-text-field
          v-model="postcode"
          outlined
          hide-details="true"
          id="postcode"
          placeholder="자동으로 입력됩니다."
          required
        ></v-text-field>
      </v-col>
      <v-col class="my-3 py-0 d-flex align-end" sm="3">
        <v-btn @click="execDaumPostcode" depressed color="accent">우편번호 찾기</v-btn>
      </v-col>
    </v-row>
    
    <!-- 주소 -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="address">주소</label>
        <v-text-field
          type="text"
          v-model="address"
          outlined
          hide-details="true"
          id="address"
          placeholder="자동으로 입력됩니다."
          required
        ></v-text-field>
      </v-col>
    </v-row>
    
    <!-- 상세 주소 -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="extraAddress">상세주소</label>
        <v-text-field
          ref="extraAddress"
          v-model="extraAddress"
          id="extraAddress"
          placeholder="상세주소를 입력해주세요."
          type="text"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>

    <!-- term -->
    <v-row class="justify-center my-4">
      <v-col sm="6">
        <v-row class="justify-space-between">
          <v-col class="py-0">
            <v-switch class="ml-2" v-model="isTerm" label="약관에 동의합니다."></v-switch>
            <v-dialog v-model="dialog" persistent max-width="290">
              <template v-slot:activator="{ on, attrs }">
                <v-btn text v-bind="attrs" v-on="on" color="primary">약관보기</v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">Indie And Bob 약관</v-card-title>
                <v-card-text>회원가입 약관입니다</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" text @click="dialog = false">닫기</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-col>

          <v-col class="text-right py-0">
            <v-btn
              @click="SignUp(
                {email: email, 
                password: password, 
                name:name,
                profile: profileImage,
                nickname: nickName,
                genreId: genre, 
                isDeveloper: is_developer, 
                phoneNumber: phonenumber, 
                bankName: bankname,
                bankAccount: accountnumber, 
                postcode: postcode, 
                address: address,
                introduction: introduction, 
                extraAddress: extraAddress})"
              :disabled="!isSubmit"
              class="d-inline-block"
              :class="{disabled : !isSubmit}"
              depressed
              large
              color="accent"
            >회원가입</v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import { mapActions, mapState } from "vuex";

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
    password: function () {
      this.checkForm();
    },
    nickName: function () {
      this.checkForm();
    },
    usertype: function () {
      if (this.usertype === "개발자") {
        this.is_developer = true;
      } else {
        this.is_developer = false;
      }
    },
    email: function () {
      this.checkForm();
      if (
        this.email.length > 0 &&
        this.email.charAt(0) >= "A" &&
        this.email.charAt(0) <= "Z"
      ) {
        this.email =
          this.email.substring(0, 1).toLowerCase() + this.email.substring(1);
      }
    },
    passwordConfirm: function () {
      this.checkForm();
    },
    phonenumber: function () {
      this.checkForm();
    },
    isTerm: function () {
      this.checkForm();
    },
  },
  methods: {
    ...mapActions(["SignUp"]),

    checkForm() {
      if (this.nickName.length <= 0)
        this.error.nickName = "왜 지웠어요? 다시 쓰세요";
      else this.error.nickName = false;

      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "올바른 email 구조가 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      if (this.passwordConfirm != this.password)
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
      else this.error.passwordConfirm = false;
      
      if ((this.phonenumber + "").length != 11)
        this.error.phonenumber = "올바른 휴대폰번호 형식이 아닙니다.";
      else this.error.phonenumber = false;

      if (this.isTerm) this.error.isTerm = false;
      else this.error.isTerm = true;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    execDaumPostcode() {
      const currentScroll = Math.max(
        document.body.scrollTop,
        document.documentElement.scrollTop
      );
      // eslint-disable-next-line
      new daum.Postcode({
        onComplete: (data) => {
          if (data.userSelectedType === "R") {
            this.address = data.roadAddress;
          } else {
            this.address = data.jibunAddress;
          }
          if (data.userSelectedType === "R") {
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extraAddress += data.bname;
            }
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extraAddress +=
                this.extraAddress !== ""
                  ? `, ${data.buildingName}`
                  : data.buildingName;
            }
            if (this.extraAddress !== "") {
              this.extraAddress = ` (${this.extraAddress})`;
            }
          } else {
            this.extraAddress = "";
          }
          this.postcode = data.zonecode;
          this.searchWindow.display = "none";
          document.getElementById("extraAddress")[0].focus();
          document.body.scrollTop = currentScroll;
        },
        
        onResize: (size) => {
          this.searchWindow.height = `${size.height}px`;
        },
        width: "100%",
        height: "100%",
      }).embed(this.$refs.searchWindow);
      this.searchWindow.display = "block";
    },
  },

  data() {
    return {
      email: "",
      name: "",
      profileImage: null,
      password: "",
      passwordConfirm: "",
      phonenumber: "",
      nickName: "",
      genre: "",
      usertype: "",
      is_developer: false,
      is_admin: false,
      userTypes: ["일반 사용자", "개발자"],
      banks: ["하나", "우리", "국민", "농협", "카카오뱅크", "신한", "IBK기업", "SC제일", "씨티"],
      bankname: "",
      accountnumber: "",
      isTerm: false,
      introduction: "",
      isLoading: false,
      error: {
        email: false,
        password: false,
        phonenumber: false,
        nickName: "",
        passwordConfirm: false,
        isTerm: false,
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false,
      passwordSchema: new PV(),
      showPw: false,
      dialog: false,
      showPwc: false,
      searchWindow: {
        display: "none",
        height: "300px",
      },
      postcode: "",
      address: "",
      extraAddress: "",
    };
  },
  computed: {
    ...mapState(['genres'])
  }
};
</script>

