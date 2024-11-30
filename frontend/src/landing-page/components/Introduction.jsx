export default function Introduction() {
  return (
    <div className="relative isolate bg-gradient-to-b from-[#CEE5FD] to-white dark:from-[#02294F] dark:to-transparent">
      <div className="mx-auto max-w-2xl py-32 sm:py-48 lg:py-80">
        <div className="hidden sm:mb-8 sm:flex sm:justify-center">
          <div className="relative rounded-full px-3 py-1 text-sm/6 text-gray-600 ring-1 ring-gray-900/10 hover:ring-gray-900/20">
            자세히 알고 싶다면?{" "}
            <a href="#" className="font-semibold text-[#2563eb]">
              <span aria-hidden="true" className="absolute inset-0" />더
              알아보기 <span aria-hidden="true">&rarr;</span>
            </a>
          </div>
        </div>
        <div className="text-center">
          <h1 className="text-balance text-5xl font-semibold tracking-tight text-gray-900 sm:text-7xl">
            ScoinOne
          </h1>
          <p className="mt-8 text-pretty text-lg font-medium text-gray-500 sm:text-xl/8">
            스트리머 방송 성적에 따라 가치가 변동되는 코인 거래 서비스
          </p>
          <div className="mt-10 flex items-center justify-center gap-x-6">
            <a
              href="#"
              className="rounded-md bg-[#2563eb] px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              거래소로 이동하기
            </a>
            <a href="#" className="text-sm/6 font-semibold text-gray-900">
              내 자산 <span aria-hidden="true">→</span>
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}
