const people = [
  {
    name: "가재맨",
    platform: "트위치 스트리머",
    role: "메이저 거래소",
    imageUrl:
      "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    listing: "1/1 상장 예정",
  },
  {
    name: "이상호",
    platform: "SOOP 스트리머",
    role: "메이저 거래소",
    imageUrl:
      "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    listing: "1/3 상장 예정",
  },
  {
    name: "감스트",
    platform: "SOOP 스트리머",
    role: "메이저 거래소",
    imageUrl:
      "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    listing: null,
  },
  {
    name: "김민교",
    platform: "SOOP 스트리머",
    role: "마이너 거래소",
    imageUrl:
      "https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    listing: "1/2 상장 예정",
  },
  {
    name: "백크",
    platform: "SOOP 스트리머",
    role: "마이너 거래소",
    imageUrl:
      "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    listing: "1/5 상장 예정",
  },
  {
    name: "우왁굳",
    platform: "SOOP 스트리머",
    role: "메이저 거래소",
    imageUrl:
      "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    listing: null,
  },
];

export default function Slider() {
  return (
    <div className="flex items-center justify-evenly p-32 sm:py-48 lg:py-72 bg-[#1c1c1c]">
      <div className="px-16 py-10 w-1/2 bg-white/5 rounded-xl">
        <ul role="list" className="divide-y divide-white/5">
          {people.map((person) => (
            <li
              key={person.platform}
              className="flex justify-between gap-x-6 py-6"
            >
              <div className="flex min-w-0 gap-x-4">
                <img
                  alt=""
                  src={person.imageUrl}
                  className="size-12 flex-none rounded-full bg-gray-50"
                />
                <div className="min-w-0 flex-auto">
                  <p className="text-lg font-semibold text-white">
                    {person.name}
                  </p>
                  <p className="mt-1 truncate text-base text-white/50">
                    {person.platform}
                  </p>
                </div>
              </div>
              <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                {person.role === "메이저 거래소" ? (
                  <p className="mt-1 text-base text-red-600">{person.role}</p>
                ) : (
                  <p className="mt-1 text-base text-yellow-500">
                    {person.role}
                  </p>
                )}
                {person.listing ? (
                  <p className="mt-1 text-base text-white/50">
                    {person.listing}
                  </p>
                ) : (
                  <div className="mt-1 flex items-center gap-x-1.5">
                    <div className="flex-none rounded-full bg-emerald-500/20 p-1">
                      <div className="size-1.5 rounded-full bg-emerald-500" />
                    </div>
                    <p className="text-base text-gray-500">상장 완료</p>
                  </div>
                )}
              </div>
            </li>
          ))}
        </ul>
      </div>
      <div className="p-10">
        <p className="text-balance text-4xl font-semibold text-white sm:text-6xl">
          새로운 Scoin
        </p>
        <p className="mt-5 text-pretty text-lg font-medium text-[#f0f0f0] sm:text-xl/8">
          지금 당장 신규 상장된 Scoin을 만나보세요
        </p>
      </div>
    </div>
  );
}
