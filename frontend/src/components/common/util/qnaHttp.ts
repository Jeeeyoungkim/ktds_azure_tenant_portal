import { QueryClient } from "@tanstack/react-query";
import axios from "axios";

export const queryClient = new QueryClient();

interface IPageProp {
  size: number;
  page: number;
  searchOption: string | null;
  searchTerm: string | boolean | null;
  fromDate: string | null;
  toDate: string | null;
}

let BASE_URL = "http://localhost:8091";

// const ENV = import.meta.env.VITE_ENV;
// if (ENV === "dev" || ENV === "prd") {
//     BASE_URL = import.meta.env.VITE_API_URL;
// }

console.log("BASE_URL", BASE_URL);

export async function saveQnABoard(data: any) {
  try {
    const response = await axios.post(
      BASE_URL + "/dash/api/v1/qna-board/save",
      data.board,
      { withCredentials: true }
    );

    if (response.status !== 200) {
      throw new Error(`Unexpected status: ${response.status}`);
    }
  } catch (error: any) {
    throw new Error(
      axios.isAxiosError(error) && error.response
        ? error.response.data
        : "An error occurred while save reqeust board data"
    );
  }
}

export async function updateQnABoard(data: any) {
  try {
    const response = await axios.patch(
      BASE_URL + "/dash/api/v1/qna-board/update",
      data.board,
      {
        headers: { "Content-Type": "application/json" },
        withCredentials: true,
      }
    );

    if (response.status !== 200) {
      throw new Error(`Unexpected status: ${response.status}`);
    }

    console.log("response", response);
  } catch (error: any) {
    throw new Error(
      axios.isAxiosError(error) && error.response
        ? error.response.data
        : "An error occurred while update reqeust board data"
    );
  }
}

export async function updateQnABoardAnswer(data: any) {
  try {
    const response = await axios.patch(
      BASE_URL + "/dash/api/v1/qna-board/update/answer",
      data.board,
      {
        headers: { "Content-Type": "application/json" },
        withCredentials: true,
      }
    );

    return response.data;
  } catch (error: any) {
    console.error("🔥 실제 에러 로그:", error); // 요거 추가

    throw new Error(
      axios.isAxiosError(error) && error.response
        ? error.response.data
        : "An error occurred while update reqeust board data"
    );
  }
}

export async function getQnABoards({
  size,
  page,
  searchOption,
  searchTerm,
  fromDate,
  toDate,
}: IPageProp) {
  // console.log(
  //     "size:",
  //     size,
  //     "page:",
  //     page,
  //     "searchOption",
  //     searchOption,
  //     "searchTerm",
  //     searchTerm,
  //     "fromDate",
  //     fromDate,
  //     "toDate",
  //     toDate
  // );

  let URL = `${BASE_URL}/dash/api/v1/qna-board/all?size=${size}&page=${page}`;

  if (
    (checkNotNull(searchOption) && checkNotNull(searchTerm)) ||
    searchOption === "date"
  ) {
    URL = `${BASE_URL}/dash/api/v1/qna-board/search?size=${size}&page=${page}`;

    if (searchOption !== "date") {
      URL += `&${searchOption}=${searchTerm}`;
    } else {
      if (fromDate && toDate) {
        URL += `&fromDate=${fromDate}&toDate=${toDate}`;
      } else if (fromDate) {
        URL += `&fromDate=${fromDate}`;
      } else if (toDate) {
        URL += `&toDate=${toDate}`;
      }
    }
  }

  //console.log("URL : ", URL);
  try {
    const response = await axios.get(URL, { withCredentials: true });

    if (response.status === 200) {
      return response.data;
    } else {
      throw new Error(`Unexpected status: ${response.status}`);
    }
  } catch (error: any) {
    throw new Error(
      axios.isAxiosError(error) && error.response
        ? error.response.data
        : "An error occurred while fetching qna board data"
    );
  }
}

export async function deleteQnABoard(data: any) {
  try {
    const response = await axios.delete(
      BASE_URL + "/dash/api/v1/qna-board/delete",
      { data: data.board, withCredentials: true }
    );

    if (response.status !== 204) {
      throw new Error(`Unexpected status: ${response.status}`);
    }
  } catch (error: any) {
    console.log(error);
    throw new Error(
      axios.isAxiosError(error) && error.response
        ? error.response.data
        : "An error occurred while delete reqeust board data"
    );
  }
}

export async function getQnABoard(
  id: string | null | undefined,
  type?: string
) {
  let URL = `${BASE_URL}/dash/api/v1/qna-board/${id}`;

  if (type === "update") {
    URL += "/update";
  }

  console.log("URL : ", URL);
  try {
    const response = await axios.get(URL, { withCredentials: true });

    if (response.status === 200) {
      return response.data;
    } else {
      throw new Error(`Unexpected status: ${response.status}`);
    }
  } catch (error: any) {
    throw new Error(
      axios.isAxiosError(error) && error.response
        ? error.response.data
        : "An error occurred while fetching board data"
    );
  }
}

function checkNotNull(data: any) {
  if (data || data === null || data == undefined || data === "") {
    return false;
  }
  return true;
}
