<template>
  <div class="h-full flex flex-col space-y-5">
    <div class="flex space-x-5 rounded-sm">
      <div class="bg-white shadow p-3 flex-grow">
        <div class="flex justify-center items-center space-x-2">
          <Input
            id="search"
            type="text"
            placeholder="상품명"
            class="border-none focus:outline-none"
          />
          <div class="pr-2">
            <Search class="text-[#3366CC]" />
          </div>
        </div>
      </div>
      <div class="bg-white shadow p-3 flex-grow">
        <Select>
          <SelectTrigger class="w-full border-none focus:outline-none">
            <SelectValue placeholder="이상치 선택" />
          </SelectTrigger>
          <SelectContent class="w-max">
            <SelectGroup>
              <SelectLabel>이상치 기준</SelectLabel>
              <SelectItem value="v1"> 이상치dsdsdsddsdsdsdA </SelectItem>
              <SelectItem value="v2"> 이상치 B </SelectItem>
              <SelectItem value="v3"> 이상치 C </SelectItem>
              <SelectItem value="v4"> 이상치 D </SelectItem>
              <SelectItem value="v5"> 이상치 E </SelectItem>
            </SelectGroup>
          </SelectContent>
        </Select>
      </div>
      <div class="bg-white shadow p-3 flex-grow">
        <div class="flex space-x-1">
          <div class="flex justify-center items-center flex-grow">
            <Popover>
              <PopoverTrigger as-child>
                <Button variant="ghost"> {{ startDate }} </Button>
              </PopoverTrigger>
              <PopoverContent>
                <Calendar v-model="startDate" :weekday-format="'short'" />
              </PopoverContent>
            </Popover>
            <CalendarIcon class="text-[#3366CC]"></CalendarIcon>
          </div>
          <div class="flex justify-center items-center flex-grow">
            <Popover>
              <PopoverTrigger as-child>
                <Button variant="ghost"> {{ endDate }} </Button>
              </PopoverTrigger>
              <PopoverContent>
                <Calendar v-model="endDate" :weekday-format="'short'" />
              </PopoverContent>
            </Popover>
            <CalendarIcon class="text-[#3366CC]"></CalendarIcon>
          </div>
        </div>
      </div>
      <div class="bg-white shadow p-3 flex-grow">
        <Input
          id="score-range"
          type="number"
          placeholder="스코어"
          class="border-none focus:outline-none"
        />
      </div>
      <div class="bg-white shadow p-3 flex-grow">
        <Select>
          <SelectTrigger class="border-none focus:outline-none">
            <SelectValue placeholder="개수" />
          </SelectTrigger>
          <SelectContent class="">
            <SelectGroup>
              <SelectItem value="10"> 10개 </SelectItem>
              <SelectItem value="15"> 15개 </SelectItem>
              <SelectItem value="20"> 20개 </SelectItem>
            </SelectGroup>
          </SelectContent>
        </Select>
      </div>
    </div>

    <div class="flex-1 p-3 bg-white shadow">
      <Table>
        <TableHeader class="text-base">
          <TableRow class="hover:bg-transparent">
            <TableHead class="text-center font-semibold text-[#333333]">
              상품명
            </TableHead>
            <TableHead class="text-center font-semibold text-[#333333]">
              이상치
            </TableHead>
            <TableHead class="text-center font-semibold text-[#333333]">
              최근 검사일
            </TableHead>
            <TableHead class="text-center font-semibold text-[#333333]">
              스코어
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow v-for="d in data" :key="d.prId">
            <TableCell class="text-center">
              {{ d.name }}
            </TableCell>
            <TableCell class="text-center">
              <div class="flex space-x-2 justify-center items-center">
                <Badge
                  v-if="d.anomaly.length != 0"
                  v-for="(anomaly, index) in d.anomaly"
                  :key="index"
                  >{{ anomaly }}</Badge
                >
                <span v-else>-</span>
              </div>
            </TableCell>
            <TableCell class="text-center">
              {{ d.lastCheck }}
            </TableCell>
            <TableCell class="text-center">
              <div class="flex items-center justify-center space-x-2">
                <span class="">{{ d.score.toFixed(1) }}</span>
                <div class="flex-1 bg-gray-200 rounded-full h-2.5">
                  <div
                    :style="{ width: d.score + '%' }"
                    :class="{
                      'bg-red-500': d.score >= 80,
                      'bg-orange-500': d.score >= 50 && d.score < 80,
                      'bg-yellow-500': d.score >= 20 && d.score < 50,
                      'bg-green-500': d.score < 20,
                    }"
                    class="h-2.5 rounded-full"
                  ></div>
                </div>
              </div>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Input } from '@/components/ui/input'
import { Search } from 'lucide-vue-next'
import { Calendar } from '@/components/ui/calendar'
import {
  type DateValue,
  getLocalTimeZone,
  today,
} from '@internationalized/date'
import { type Ref, ref } from 'vue'
import { Calendar as CalendarIcon } from 'lucide-vue-next'

const startDate = ref(today(getLocalTimeZone())) as Ref<DateValue>
const endDate = ref(today(getLocalTimeZone())) as Ref<DateValue>

const data = [
  {
    prId: 10515454,
    name: 'INV001',
    anomaly: ['이상치 A', '이상치 B'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 80,
  },
  {
    prId: 10515455,
    name: 'INV001',
    anomaly: ['이상치 A', '이상치 B', '이상치 C', '이상치 E'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 13.5,
  },
  {
    prId: 10515456,
    name: 'INV001',
    anomaly: ['이상치 A'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 54.7,
  },
  {
    prId: 10515454,
    name: 'INV001',
    anomaly: [],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 33,
  },
  {
    prId: 10515454,
    name: 'INV001',
    anomaly: ['이상치 A', '이상치 B', '이상치 C', '이상치 E', '이상치 D'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 97,
  },
  {
    prId: 10515454,
    name: 'INV001',
    anomaly: ['이상치 A', '이상치 B', '이상치 C'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 43,
  },
]
</script>

<style scoped></style>
