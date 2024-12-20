import { Handle, Position, type NodeProps } from "reactflow";
import { IoPlay } from "@react-icons/all-files/io5/IoPlay";
import { NodeData } from "@/types/chatbot";

export default function StartNode({
  data, selected
}:
  NodeProps<NodeData>
) {

  return (
    <>
      <div className={`p-2 bg-[#ECF3E0] rounded-[16px] border-[#95C447] text-[10px] w-[145px]
        ${selected ? "border-[2px]" : "border-[1px]"}
        ${data.isComplete ? "border-[5px]" : ""}
        ${data.isError ? "border-[5px] border-[#ff0000]" : "border-[#95C447]"}`}
      >
        <div className="flex flex-col gap-1.5 relative">
          <div className="flex flex-row justify-between items-center">
            <div className="flex flex-row items-center gap-1">
              <IoPlay className="text-[#95C447] size-3" />
              <div className="text-[11px] font-semibold w-[90px]">{data.name}</div>
            </div>
          </div>
          {typeof data.maxLength === "number" && !isNaN(data.maxLength) && (
            <div className="flex flex-col gap-0.5 text-[8px]">
              <div className="text-[8px]">최대 입력 글자수</div>
              <div className="rounded-[5px] p-0.5 bg-white">{data.maxLength}</div>
            </div>
          )}
        </div>
        <Handle type="source" position={Position.Right} />
      </div>
    </>
  );
}