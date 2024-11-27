// 다크모드 구현 예정
import { useState } from "react";
import { XMarkIcon, MagnifyingGlassIcon } from "@heroicons/react/24/outline";

const menuItems = [
  { name: "거래소", href: "#" },
  { name: "내 자산", href: "#" },
];

export default function Navigation2() {
  const [searchTerm, setSearchTerm] = useState("");

  return (
    <>
      <Banner />
      <nav className="w-full border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center">
              <div className="flex items-center mr-8">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="#2563eb"
                  className="size-6 mr-1"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M12 18.75a6 6 0 0 0 6-6v-1.5m-6 7.5a6 6 0 0 1-6-6v-1.5m6 7.5v3.75m-3.75 0h7.5M12 15.75a3 3 0 0 1-3-3V4.5a3 3 0 1 1 6 0v8.25a3 3 0 0 1-3 3Z"
                  />
                </svg>
                <div className="w-32">
                  <span className="text-2xl font-semibold text-[#2563eb]">
                    ScoinOne
                  </span>
                </div>
              </div>

              <div className="flex space-x-8">
                {menuItems.map((item) => (
                  <a
                    key={item.name}
                    href={item.href}
                    className="text-gray-600 hover:text-[#2563eb] text-md font-bold transition-colors"
                  >
                    {item.name}
                  </a>
                ))}
              </div>
            </div>

            <div className="flex-1 flex justify-end mr-10">
              <div className="w-80 max-w-md relative">
                <MagnifyingGlassIcon className="size-5 absolute left-3 top-1/2 -translate-y-1/2 text-[#2563eb]" />
                <input
                  type="text"
                  placeholder="가상자산 검색"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-200 
                focus:outline-none focus:ring-2 focus:ring-[#2563eb]/20 
                focus:border-[#2563eb] bg-gray-50 
                hover:bg-white transition-colors
                placeholder:text-gray-400 text-gray-900 font-bold"
                />
              </div>
            </div>

            <div className="flex items-center space-x-4">
              <a
                href="#"
                className="text-gray-600 hover:text-[#2563eb] text-md font-bold transition-colors"
              >
                로그인
              </a>
              <a
                href="#"
                className="text-white bg-[#2563eb] hover:bg-[#1d4ed8] 
                px-3 py-1.5 rounded-md text-md font-bold transition-colors"
              >
                회원가입
              </a>
            </div>
          </div>
        </div>
      </nav>
    </>
  );
}

const Banner = () => {
  return (
    <div className="relative isolate flex items-center gap-x-6 overflow-hidden bg-[#60a5fa] px-6 py-2.5 sm:px-3.5 sm:before:flex-1">
      <div
        aria-hidden="true"
        className="absolute left-[max(-7rem,calc(50%-52rem))] top-1/2 -z-10 -translate-y-1/2 transform-gpu blur-2xl"
      ></div>
      <div
        aria-hidden="true"
        className="absolute left-[max(45rem,calc(50%+8rem))] top-1/2 -z-10 -translate-y-1/2 transform-gpu blur-2xl"
      ></div>
      <div className="flex flex-wrap items-center gap-x-4 gap-y-2">
        <p className="text-sm/6 text-gray-900">
          <strong className="font-semibold">오픈 이벤트</strong>
          <svg
            viewBox="0 0 2 2"
            aria-hidden="true"
            className="mx-2 inline size-0.5 fill-current"
          >
            <circle r={1} cx={1} cy={1} />
          </svg>
          신규 회원은 일주일 동안 거래 수수료 무료!
        </p>
        <a
          href="#"
          className="flex-none rounded-full bg-gray-900 px-3.5 py-1 text-sm font-semibold text-white shadow-sm hover:bg-gray-700 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-gray-900"
        >
          지금 당장 가입하기 <span aria-hidden="true">&rarr;</span>
        </a>
      </div>
      <div className="flex flex-1 justify-end">
        {/* <button
          type="button"
          className="-m-3 p-3 focus-visible:outline-offset-[-4px]"
        >
          <span className="sr-only">Dismiss</span>
          <XMarkIcon aria-hidden="true" className="size-5 text-gray-900" />
        </button> */}
      </div>
    </div>
  );
};
