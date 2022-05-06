<template>
  <div class="editor-body">
    
    <div v-if="editor">
      <transition name="fade">
        <drag-and-drop-modal ref="dragModal" @receiveNoteImg="receiveNoteImg" />
      </transition>

      <div class="text-editor-btn-area" v-if="toolbarBool && editable">
        <button @click="editor.chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
          <img src="https://img.icons8.com/fluency-systems-regular/344/bold.png" alt="bold">
        </button>
        <button @click="editor.chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
          <img src="https://img.icons8.com/material-rounded/344/italic.png" alt="italic">
        </button>
        <button @click="editor.chain().focus().toggleUnderline().run()" :class="{ 'is-active': editor.isActive('underline') }">
          <img src="https://img.icons8.com/material-rounded/344/underline.png" alt="underline">
        </button>
        <button @click="editor.chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
          <img src="https://img.icons8.com/material-rounded/344/strikethrough.png" alt="strike">
        </button>
        <img src="https://img.icons8.com/material-rounded/344/vertical-line.png" alt="vertical_bar">
        <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
          <img src="https://img.icons8.com/material-rounded/344/header-1.png" alt="h1">
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
          <img src="https://img.icons8.com/material-rounded/344/header-2.png" alt="h2">
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
          <img src="https://img.icons8.com/material-rounded/344/header-3.png" alt="h3">
        </button>
        <img src="https://img.icons8.com/material-rounded/344/vertical-line.png" alt="vertical_bar">
        <button @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }">
          <img src="https://img.icons8.com/material-rounded/344/list.png" alt="bullet-list">
        </button>
        <button @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }">
          <img src="https://img.icons8.com/material-rounded/344/numbered-list.png" alt="ordered-list">
        </button>
        <img src="https://img.icons8.com/material-rounded/344/vertical-line.png" alt="vertical_bar">
        <button @click="editor.chain().focus().setTextAlign('left').run()" :class="{ 'is-active': editor.isActive({ textAlign: 'left' }) }">
          <img src="https://img.icons8.com/material-rounded/344/align-left.png" alt="left">
        </button>
        <button @click="editor.chain().focus().setTextAlign('center').run()" :class="{ 'is-active': editor.isActive({ textAlign: 'center' }) }">
          <img src="https://img.icons8.com/material-rounded/344/align-center.png" alt="center">
        </button>
        <button @click="editor.chain().focus().setTextAlign('right').run()" :class="{ 'is-active': editor.isActive({ textAlign: 'right' }) }">
          <img src="https://img.icons8.com/material-rounded/344/align-right.png" alt="right">
        </button>
        <button @click="editor.chain().focus().setTextAlign('justify').run()" :class="{ 'is-active': editor.isActive({ textAlign: 'justify' }) }">
          <img src="https://img.icons8.com/material-rounded/344/align-justify.png" alt="justify">
        </button>
    
        <img src="https://img.icons8.com/material-rounded/344/vertical-line.png" alt="vertical_bar">
        <button @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'is-active': editor.isActive('codeBlock') }">
          <img src="https://img.icons8.com/material-outlined/344/code.png" alt="code-block">
        </button>
        <button @click="editor.chain().focus().toggleCode().run()" :class="{ 'is-active': editor.isActive('code') }">
          <img src="https://img.icons8.com/material-rounded/344/source-code.png" alt="source">
        </button>
        <button @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'is-active': editor.isActive('blockquote') }">
          <img src="https://img.icons8.com/material-rounded/344/quote-left.png" alt="block-quote">
        </button>
        <button @click="editor.chain().focus().setHorizontalRule().run()">
          <img src="https://img.icons8.com/material-rounded/344/horizontal-line.png" alt="horizontal-rule">
        </button>
    
        <button @click="addImage">
          <img src="https://img.icons8.com/material-rounded/344/image.png" alt="image">
        </button>
        <img src="https://img.icons8.com/material-rounded/344/vertical-line.png" alt="vertical_bar">
        <input
        class="text-editor-color-peeker"
        type="color"
        @input="editor.chain().focus().setColor($event.target.value).run()"
        :value="editor.getAttributes('textStyle').color">
      </div>
    </div>
    <editor-content 
        ref="textEditor" :editor="editor" class="edit-content" />
  </div>
</template>

<script>
import { Editor, EditorContent } from '@tiptap/vue-3';
import Image from '@tiptap/extension-image';
import StarterKit from '@tiptap/starter-kit';
import TextStyle from '@tiptap/extension-text-style';
import { Color } from '@tiptap/extension-color';
import TextAlign from '@tiptap/extension-text-align';
import Underline from '@tiptap/extension-underline';
import DragAndDropModal from './DragAndDropModal.vue';
import axios from 'axios';

export default {
  components: {
    EditorContent,
    DragAndDropModal,
  },
  props: ["isEditable", "curationContents", "expand", "toolbar", "unset"],
  data() {
    return {
      editor: null,
      // extendsHeight: this.extend,
      contents: '<p>내용을 입력해 주세요</p>',
      editable: this.isEditable,
      toolbarBool: this.toolbar,
      imgList: []
    }
  },
  methods: {
    sample() {
      console.log("ASDFASDF");
      // this.editor.setContents(contents)
    },
    addImage() {
      this.$refs.dragModal.openModal();
    },
    receiveNoteImg(imgList) {
      const key = this.$store.state.imbbKey;
      let sendImgList = [];
      let body = new FormData();
      if(imgList.length != 0) {

        for(let i = 0; i < imgList.length; i++) {

          body.set('key', key);
          body.append('image', imgList[i]);
          
          axios.post(`https://api.imgbb.com/1/upload?key=${key}`, body)
            .then(res => {
                console.log(res)
                console.log(res.data.data.display_url)
                const imgUrl = res.data.data.display_url
                this.editor.chain().focus().setImage({ src: imgUrl }).run();

                sendImgList.push(imgUrl);
              })
            }
            console.log("sendImgList", sendImgList);
        this.imgList = sendImgList;
      }
    },
    sendContents() {
      var contents = this.editor.getHTML();
      this.$emit('sendContents', contents, this.imgList);
    },
  },

  mounted() {
    this.editor = new Editor({
      extensions: [
        StarterKit,
        Image,
        Color,
        TextStyle,
        Underline,
        TextAlign.configure({
          types: ['heading', 'paragraph'],
        }),
      ],
      editorProps: {
        attributes: {
          class: `${this.unset} ${this.expand}`
        }
      },
      content: this.curationContents || this.contents,
      editable: this.editable
    })
  },

  beforeUnmount() {
    this.editor.destroy()
  },
}
</script>

<style lang="scss">
.text-editor-btn-area {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 5px;
  border-bottom: 1px solid black;
  padding-bottom: 10px;
}
.text-editor-btn-area > img {
  width: 30px;
  height: 40px; 
}
.text-editor-btn-area button {
  background: white;
  border: none;
  border-radius: 5px;
  margin: 0 2px;
  padding-top: 5px;
  font-size: 16px;
  font-weight: bold;
  flex-grow: 1;
}
button img {
  width: 24px;
  height: 24px;
  object-fit: cover;
}

.text-editor-color-peeker {
  width: 50px;
  height: 40px;
  background: white;
  border: none;
  margin: 0 2px;
  padding: 3px;
}

// 에디터 관련 구간 
.ProseMirror{

  padding: 20px;
  height: 370px;
  overflow: scroll;

> * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0D0D0D;
    color: #FFF;
    font-family: 'AlegreyaSans', sans-serif;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }

  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }

}
.edit-content *:focus {
  outline: none;
}
.ProseMirror::-webkit-scrollbar{
  display: none;
  width: 0
}
.expandEditor {
  height: 700px;
}
.scrollUnset {
  overflow: unset;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity .2s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>