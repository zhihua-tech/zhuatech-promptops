# ZhuaTech PromptOps｜企业提示词运营与发布平台

2026-10-23 功能更新：**提示词变量检查与安全渲染**。检测缺失、额外、重复变量及异常括号；使用字面量替换，不递归执行变量内容，返回变量位置。不会把变量中的模板或脚本当作指令执行。 [接口、操作与边界说明](docs/AI_PRACTICAL_UPGRADE_20261023.md)。

[简体中文](README.md) | [English](README.en.md)

> 从提示词模板、变量、评测到审批发布，建立可版本化、可回归、可审计的企业 PromptOps 闭环。

ZhuaTech PromptOps 是 **[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)** 发布的企业 AI 提示词运营社区源码版，采用 Java 21、Spring Boot 4、Vue 3 和 MySQL 构建。


## 本次升级：提示词实验收益与回归门槛

比较同一评测集的新旧提示词成功率、失败增量和每成功任务成本；同时检查最小样本，不把小样本变好当作发布批准。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。
## 已实现的核心能力

- 模板变量解析和真实渲染，阻止缺失变量与未解析占位符发布。
- 检测“忽略系统指令、泄露系统提示、越权”等提示注入信号。
- 质量分、安全分、回归通过率、单次成本预算联合门禁。
- 提示词负责人和审批人职责分离，输出风险等级和审批路径。
- 请求幂等、不可重复发布评估、MySQL 审计记录与审计员查询。
- HTTP Basic + BCrypt 的管理员、运营员、审计员角色控制。
- Vue 管理台可提交真实评估请求并查看最近发布审计。

## 业务闭环

```text
模板设计 → 变量校验 → 注入扫描 → 回归评测 → 成本门禁 → 独立审批 → 发布/阻断 → 审计
```

## 主要接口

- `POST /api/promptops/releases/evaluate`：执行发布评估并持久化结果。
- `GET /api/promptops/audits`：查询最近 100 条审计记录。

## 本地启动

```bash
cp .env.example .env
docker compose up --build
```

访问 `http://localhost:8091`。演示环境请在 `.env` 设置账号密码；生产必须接入企业身份源、TLS 和密钥管理服务。

## 测试

```bash
cd backend && mvn test
cd frontend && npm install && npm run build
```

## 使用限制

> [!IMPORTANT]
> 本工程仅允许个人、非商业性的学习、研究和技术交流，不得商用。企业内部使用、生产部署、SaaS、实施交付、培训、咨询、外包和二次销售均须取得上海如静知华信息科技有限公司书面授权。详见 [LICENSE](LICENSE)。

深度定制、企业 AI 转型、私有模型接入和商业授权，请联系[知华科技](https://www.zhuatech.cn/)。

## 提示词 A/B 实验发布

新增 `POST /api/promptops/experiments/decision`，综合双臂样本量、统计置信度、任务成功率、安全事件、P95 延迟、单位成本、护栏和负责人审批，输出 `PROMOTE / CONTINUE / STOP / ROLLBACK`，避免只看单一效果指标直接替换生产提示词。

## 微信咨询

商业授权、私有化部署或深度定制开发，可扫描下方二维码添加微信 `zhuatech` 或 `zhuatech2` 咨询。

| 微信 zhuatech | 微信 zhuatech2 |
| --- | --- |
| ![知华科技微信咨询 zhuatech](docs/images/zhuatech-wechat-consulting.png) | ![知华科技微信咨询 zhuatech2](docs/images/zhuatech-wechat-consulting-2.png) |

## 输入对照与版本化快照

在既有「提示词实验收益与回归门槛」基础上，新增 `/comparison.html`：基准与候选两组数据都由当前真实算法重算，按编号展示新增、移除、逐字段变化和精确数值差，支持筛选、分页和全量导出。原核算入口可导出输入快照，对照页经后端完整校验后整体导入；失败不覆盖当前输入。

快照区分项目、字段契约及算法版本。建议较宽松不代表风险已下降，输入摘要不是签名；操作人和时间仅为响应元数据，不是持久化审计。本次未新增模型调用或业务写入，也不代表全系统生产认证。详见[输入对照操作与接口说明](docs/INPUT-COMPARISON.md)。
