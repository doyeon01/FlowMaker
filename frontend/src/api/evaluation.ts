import axiosInstance from '@/api/token/axiosInstance';


// 챗플로우 테스트 목록 조회
export async function getChatFlowTestList(chatFlowId: string) {
  try {
    const response = await axiosInstance.get(`/chat-flows/${chatFlowId}/tests`);
    if (response.status === 200) {
      return response.data.data 
    } else {
      throw new Error('Failed to get knowledges');
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}



// 챗플로우 테스트 생성
export async function postChatFlowTest(chatFlowId: string, data: { testQuestion: string; groundTruth: string }[]) {
  try {
    const response = await axiosInstance.post(`/chat-flows/${chatFlowId}/tests`, data);
    if (response.status === 200) {
      return response.data.data 
    } else {
      throw new Error('Failed to get knowledges');
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
}

