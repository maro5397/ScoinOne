import BoardList from "../commons/BoardList";
import BaseLayout from "../commons/BaseLayout";

export default function BoardPage() {
  return (
    <BaseLayout marginTop={100}>
      <BoardList></BoardList>
    </BaseLayout>
  );
}
