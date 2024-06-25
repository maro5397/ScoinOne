import BoardList from "../commons/BoardList";
import BaseLayout from "../commons/BaseLayout";

export default function BoardPage({ path, pageName }) {
  return (
    <BaseLayout marginTop={100}>
      <BoardList path={path} pageName={pageName}></BoardList>
    </BaseLayout>
  );
}
