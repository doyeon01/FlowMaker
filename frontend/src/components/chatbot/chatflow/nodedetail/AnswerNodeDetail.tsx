import { FaRobot } from "@react-icons/all-files/fa/FaRobot"
import { FiBookOpen } from "@react-icons/all-files/fi/FiBookOpen"
import { RiQuestionAnswerFill } from "@react-icons/all-files/ri/RiQuestionAnswerFill"
import { GrTree } from "@react-icons/all-files/gr/GrTree"
import { CgClose } from "@react-icons/all-files/cg/CgClose"
import { Dispatch, SetStateAction, useCallback, useEffect, useState } from "react";
import { ConnectedNode } from "@/types/workflow";
import { nodeConfig, deleteIconColors } from "@/utils/nodeConfig";
import { AiOutlineClose } from "@react-icons/all-files/ai/AiOutlineClose";
import { Node } from "reactflow";

export default function AnswerNodeDetail({
  node,
  setNodes,
  onClose,
}: {
  node: Node<any, string | undefined>,
  setNodes: Dispatch<SetStateAction<Node<any, string | undefined>[]>>
  onClose: () => void
}) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };


  const handleAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    var value: string = event.target.value;

    // node의 데이터 수정
    node.data.outputMessage = value;

    // 상태 업데이트
    setNodes((prevNodes: Node[]) =>
      prevNodes.map((n) =>
        n.id === node.id ? node : n
      )
    );
  };

  // const handleNodeTypeClick = useCallback(
  //   (type: string) => {
  //     addNode(type);
  //     onClose();
  //   },
  //   [addNode, onClose]
  // );

  return <>
    <div className="flex flex-col gap-4 w-[320px] h-[calc(100vh-170px)] rounded-[20px] p-[20px] bg-white bg-opacity-40 backdrop-blur-[15px] shadow-[0px_2px_8px_rgba(0,0,0,0.25)] overflow-y-auto">
      <div className="flex flex-row justify-between items-center mb-2">
        <div className="flex flex-row items-center gap-1">
          <RiQuestionAnswerFill className="text-[#34D399] size-8" />
          <div className="text-[25px] font-semibold">답변</div>
        </div>
        <CgClose className="size-6 cursor-pointer" onClick={onClose} />
      </div>

      <div className="flex flex-col gap-2">
        <div className="text-[16px]">답변을 입력하세요.</div>
        <textarea
          value={node.data.outputMessage || ""}
          onChange={handleAnswerChange}
          className="p-2 bg-white rounded-[5px] w-full resize-none overflow-hidden mt-2 focus:outline-none shadow-none border-none"
          style={{ minHeight: "50px" }}
        />
      </div>
      <div className="flex flex-col gap-2">
        <div className="text-[16px]">다음 블록을 추가하세요.</div>
        <div className="flex flex-row justify-between w-full">
          <div className="aspect-square bg-[#BAEEDB] rounded-[360px] w-[50px] h-[50px] flex justify-center items-center z-[10]">
            <RiQuestionAnswerFill className="text-[#34D399] size-8" />
          </div>
          <div className="bg-black h-[2px] w-[230px] flex-grow my-[24px]"></div>

          <div className="z-[10] w-[160px] mt-[6px]">
            {/* {connectedNodes.map((node, index) => (
              <div
                key={index}
                className={`inline-flex items-center gap-2 w-[160px] rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-[#${nodeConfig[node.name]?.color}] text-sm font-medium focus:outline-none focus:ring-1 focus:ring-[#95C447]`}
              >
                {nodeConfig[node.name]?.icon}
                <span>{nodeConfig[node.name]?.label || node.name}</span>
                <AiOutlineClose
                  className="cursor-pointer ml-auto"
                  style={{
                    color: deleteIconColors[node.name] || "gray",
                  }}
                  onClick={() => setConnectedNodes(node.nodeId)}
                />
              </div>
            ))} */}


            <div className="relative inline-block text-left">
              <div>
                <button
                  type="button"
                  className="inline-flex justify-center w-[160px] rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-1 focus:ring-[#34D399]"
                  onClick={toggleDropdown}
                >
                  다음 블록 선택
                  <svg
                    className="-mr-1 ml-2 h-5 w-5"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                    fill="currentColor"
                    aria-hidden="true"
                  >
                    <path
                      fillRule="evenodd"
                      d="M5.23 7.21a.75.75 0 011.06.02L10 10.94l3.71-3.71a.75.75 0 111.06 1.06l-4 4a.75.75 0 01-1.06 0l-4-4a.75.75 0 01.02-1.06z"
                      clipRule="evenodd"
                    />
                  </svg>
                </button>
              </div>

              {isOpen && (
                <div
                  className="origin-top-right absolute right-0 mt-2 w-[160px] rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5"
                  role="menu"
                  aria-orientation="vertical"
                  aria-labelledby="menu-button"
                >
                  <div className="p-1 text-[15px]" role="none">
                    <div
                      // onClick={() => handleNodeTypeClick("LLM")}
                      className="hover:bg-[#f4f4f4] px-4 py-1.5 cursor-pointer flex flex-row items-center gap-2"
                    >
                      <FaRobot className="text-[18px]" />
                      <div>LLM</div>
                    </div>
                    <div
                      // onClick={() => handleNodeTypeClick("RETRIEVER")}
                      className="hover:bg-[#f4f4f4] px-4 py-1.5 cursor-pointer flex flex-row items-center gap-2"
                    >
                      <FiBookOpen className="text-[18px]" />
                      <div>지식 검색</div>
                    </div>
                    <div
                      // onClick={() => handleNodeTypeClick("ANSWER")}
                      className="hover:bg-[#f4f4f4] px-4 py-1.5 cursor-pointer flex flex-row items-center gap-2"
                    >
                      <RiQuestionAnswerFill className="text-[18px]" />
                      <div>답변</div>
                    </div>
                    <div
                      // onClick={() => handleNodeTypeClick("QUESTION_CLASSIFIER")}
                      className="hover:bg-[#f4f4f4] px-4 py-1.5 cursor-pointer flex flex-row items-center gap-2"
                    >
                      <GrTree className="text-[18px]" />
                      <div>질문 분류기</div>
                    </div>
                    {/* <div
                      onClick={() => handleNodeTypeClick("CONDITIONAL")}
                      className="hover:bg-[#f4f4f4] px-4 py-1.5 cursor-pointer flex flex-row items-center gap-2"
                    >
                      <IoGitBranchOutline className="text-[18px]" />
                      <div>IF/ELSE</div>
                    </div>
                    <div
                      onClick={() => handleNodeTypeClick("VARIABLE_ASSIGNER")}
                      className="hover:bg-[#f4f4f4] px-4 py-1.5 cursor-pointer flex flex-row items-center gap-2"
                    >
                      <VscSymbolVariable className="text-[18px]" />
                      <div>변수 할당자</div>
                    </div> */}
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  </>
}