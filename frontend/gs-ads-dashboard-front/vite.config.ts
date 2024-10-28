import vue from '@vitejs/plugin-vue'
import autoprefixer from 'autoprefixer'
import path from 'path'
import tailwind from 'tailwindcss'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  css: {
    postcss: {
      plugins: [tailwind(), autoprefixer()],
    },
  },
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.join(__dirname, 'src/'),
    },
  },
})
