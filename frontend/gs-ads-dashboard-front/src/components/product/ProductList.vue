<template>
  <div class="h-full flex flex-col space-y-5">
    <div class="flex space-x-5 rounded-sm">
      <div class="bg-white shadow p-3 flex-grow">
        <div class="flex justify-center items-center space-x-2">
          <Input id="search" type="text" placeholder="상품명" class="border-none focus:outline-none" />
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
        <Input id="score-range" type="number" placeholder="스코어" class="border-none focus:outline-none" />
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

    <div class="flex-1 p-3 bg-white shadow flex flex-col space-y-10 justify-center items-center">
      <Table class="flex=1">
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
          <TableRow v-for="d in data" :key="d.prId" class="cursor-pointer" @click="goToDetail(d.prId)">
            <TableCell class="text-center">
              {{ d.name }}
            </TableCell>
            <TableCell class="text-center">
              <div class="flex space-x-2 justify-center items-center">
                <Badge v-if="d.anomaly.length != 0" v-for="(anomaly, index) in d.anomaly" :key="index">{{ anomaly }}
                </Badge>
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
                  <div :style="{ width: d.score + '%' }" :class="{
                    'bg-red-500': d.score >= 80,
                    'bg-orange-500': d.score >= 50 && d.score < 80,
                    'bg-yellow-500': d.score >= 20 && d.score < 50,
                    'bg-green-500': d.score < 20,
                  }" class="h-2.5 rounded-full"></div>
                </div>
              </div>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
      <Pagination v-model:page="curPage" v-slot="{ page }" :total="totalPages * 10">
        <PaginationList v-slot="{ items }" class="flex items-center gap-5">
          <PaginationFirst class="border-none" />
          <PaginationPrev class="border-none" />

          <!-- 현재 페이지 그룹 표시 -->
          <template v-for="(item, index) in items">
            <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
              <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'ghost'">
                {{ item.value }}
              </Button>
            </PaginationListItem>
            <!-- <PaginationEllipsis v-else :key="item.type" :index="index" /> -->
          </template>

          <PaginationNext class="border-none" />
          <PaginationLast class="border-none" />
        </PaginationList>
      </Pagination>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'
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
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'

import {
  Pagination,
  PaginationFirst,
  PaginationLast,
  PaginationList,
  PaginationListItem,
  PaginationNext,
  PaginationPrev,
} from '@/components/ui/pagination'

import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Calendar } from '@/components/ui/calendar'
import { Input } from '@/components/ui/input'
import {
  type DateValue,
  getLocalTimeZone,
  today,
} from '@internationalized/date'
import { Calendar as CalendarIcon, Search } from 'lucide-vue-next'
import { type Ref, ref, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const startDate = ref(today(getLocalTimeZone())) as Ref<DateValue>
const endDate = ref(today(getLocalTimeZone())) as Ref<DateValue>
const totalPages = ref(34) // 전체 페이지 수// 현재 페이지 그룹의 시작 페이지
const curPage = ref(1)
watch(
  () => curPage.value,
  (newPage) => {
    console.log('페이지가 변경되었습니다. 현재 페이지:', newPage)
    // 페이지 변경 시 수행할 작업을 여기에 추가합니다.
  },
)
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
    prId: 10515455,
    name: 'INV001',
    anomaly: ['이상치 A', '이상치 B', '이상치 C', '이상치 E'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 13.5,
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
    prId: 10515455,
    name: 'INV001',
    anomaly: ['이상치 A', '이상치 B', '이상치 C', '이상치 E'],
    regDate: '2024-10-30',
    category: 'Credit Card',
    lastCheck: '2024-10-30 17:35:20',
    score: 13.5,
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

const goToDetail = (prId: number) => {
  router.push({ name: 'ProductDetail', params: { id: prId } })
}
</script>

<style scoped></style>
