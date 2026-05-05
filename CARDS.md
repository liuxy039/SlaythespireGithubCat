# 卡牌列表

## BASIC（起始卡）
| 名称 | ID | 费用 | 效果 |
|------|----|------|------|
| 打击 | `githubcat:Strike` | 1 | 造成100伤害 |
| 防御 | `githubcat:Defend` | 1 | 获得100格挡 |

## COMMON（白色）
| 名称 | ID | 费用 | 效果 |
|------|----|------|------|
| 自残 | `githubcat:SelfHarm` | 0 | 失1血，抽1(2)张 |
| 爪击 | `githubcat:Scratch` | 1 | 20伤，自伤一半(四分之一) |
| 伏地姿态 | `githubcat:GroundPose` | 2(1) | 10格挡，2伤×2次 |
| 推送云端 | `githubcat:PushToCloud` | 1 | 选最多2(3)张手牌上传仓库 |
| 死线将至 | `githubcat:Deadline` | 1 | 随机上传2张手牌，升级后保留 |
| 居家办公 | `githubcat:WorkFromHome` | 0 | 消耗1层WiFi，仓库选1牌到手牌 |
| 事务隔离 | `githubcat:TransactionIsolation` | 1 | 5格挡，加入手牌时抽1(2)牌 |
| 紧急补丁 | `githubcat:EmergencyPatch` | 1 | 6(8)格挡，加入手牌时抽1 |
| 闲置回收 | `githubcat:IdleRecycle` | 1 | 10格挡，被删除时得3能量 |
| 勒索攻击 | `githubcat:Ransomware` | 1 | 7伤，加入手牌时得1能量 |

## UNCOMMON（绿色）
| 名称 | ID | 费用 | 效果 |
|------|----|------|------|
| 科学上网 | `githubcat:ScienceSurf` | 1 | 抽1牌，得1(2)层WiFi |
| 拉取 | `githubcat:PullFromCloud` | 0 | 箭头选仓库1牌到手牌，升级后费用-1 |
| 快速开发 | `githubcat:ScrumMaster` | 3 | 抽2牌得1(2)能量，消耗。离开仓库时直接打出 |
| 防火墙 | `githubcat:Firewall` | 1 | 2(3)金属化，消耗。离开仓库时数值翻倍 |
| 互联网精神 | `githubcat:InternetSpirit` | 2（能力） | 每回合多抽1牌，可选1手牌上传 |
| 阿三程序员 | `githubcat:IndianDev` | 1(0) | 从3随机牌选1上传仓库 |
| 代码审查 | `githubcat:CodeReview` | 0 | 抽1牌，仓库有同名牌得1能量 |
| 热修复 | `githubcat:Hotfix` | 0 | 上传弃牌堆最上1(2)张，技能牌则抽1 |
| 垃圾回收 | `githubcat:GarbageCollect` | 2 | 删除仓库所有牌，每张得2(3)格挡 |
| 数据包风暴 | `githubcat:PacketStorm` | 1 | 4(6)伤×WiFi层数(最多4) |
| 便携路由器 | `githubcat:PortableRouter` | 2 | 得2层WiFi，被上传时得1层WiFi |
| 资源调度 | `githubcat:ResourceSched` | 4 | 得1(2)能量，每次加入手牌费用-1 |

## RARE（金色）
| 名称 | ID | 费用 | 效果 |
|------|----|------|------|
| 断尾 | `githubcat:TailCut` | 3(2) | 半血，1(2)无实体 |
| 删库跑路 | `githubcat:DBDrop` | 3 | 删除仓库所有牌，每张对全体6(8)伤 |
| 开源协议 | `githubcat:OpenSource` | 1（能力） | 每上传1牌，得1临时力量+敏捷 |
| 黑客入侵 | `githubcat:HackAttack` | 2 | 20伤，被上传时永久+5攻 |
| 仓库重载 | `githubcat:StorageReload` | 3(2) | 消耗所有手牌+1WiFi，仓库牌全入牌 |

## 遗物
| 名称 | ID | 效果 |
|------|----|------|
| GitHubDesktop | `githubcat:GitHubDesktop` | 战斗开始选3手牌上传，每回合1层WiFi |
| 小Clash | `githubcat:LittleClash` | 战斗结束回10血 |
